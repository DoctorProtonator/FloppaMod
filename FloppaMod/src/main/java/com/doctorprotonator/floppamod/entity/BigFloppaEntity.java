package com.doctorprotonator.floppamod.entity;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.doctorprotonator.floppamod.entity.goals.HissGoal;
import com.doctorprotonator.floppamod.init.EntityInit;
import com.doctorprotonator.floppamod.init.ItemInit;
import com.doctorprotonator.floppamod.init.SoundInit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BigFloppaEntity extends TamableAnimal implements NeutralMob, IAnimatable
{
	private AnimationFactory factory = new AnimationFactory(this);
	
	public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.COOKED_BEEF, Items.RABBIT, Items.COOKED_RABBIT, ItemInit.SHRIMP.get(), ItemInit.CEMENT_BAG.get());
	
	public static final Predicate<LivingEntity> PREY_SELECTOR = (prey) ->
	{
		EntityType<?> entitytype = prey.getType();
		return entitytype == EntityType.CHICKEN || entitytype == EntityType.RABBIT;
	};
	
	private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(BigFloppaEntity.class, EntityDataSerializers.INT);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private UUID persistentAngerTarget;
	
	private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(BigFloppaEntity.class, EntityDataSerializers.BOOLEAN);
	
	public BigFloppaEntity(EntityType<? extends TamableAnimal> entityType, Level level)
	{
		super(entityType, level);
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		if(event.isMoving() && this.isSitting() == false)
		{
			event.getController().setAnimation(new AnimationBuilder().addAnimation("walking.big_floppa.anim", true));
			event.getController().setAnimationSpeed(2.7f);
			return PlayState.CONTINUE;
		}
		else if(this.isSitting() == true)
		{
			if(HissGoal.isHissing == true)
			{
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.big_floppa.hissing_sit", false));
				event.getController().setAnimationSpeed(.15f);
				return PlayState.CONTINUE;
			}
			else
			{
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.big_floppa.sitting", true));
				event.getController().setAnimationSpeed(1f);
				return PlayState.CONTINUE;
			}
		}
		else
		{
			if(HissGoal.isHissing == true)
			{
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.big_floppa.hissing_idle", false));
				event.getController().setAnimationSpeed(.15f);
				return PlayState.CONTINUE;
			}
			else
			{
				event.getController().setAnimation(new AnimationBuilder().addAnimation("idle.big_floppa.anim", true));
				event.getController().setAnimationSpeed(1f);
				return PlayState.CONTINUE;
			}
		}
	}
	
	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController<BigFloppaEntity>(this, "controller", 0, this::predicate));
	}
	
	@Override
	public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_)
	{
		return false;
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}
	
	public static AttributeSupplier.Builder createAttributes()
	{
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 20.0f)
	    		.add(Attributes.ATTACK_DAMAGE, 5.0f)
	    		.add(Attributes.ATTACK_SPEED, 0.75f)
	    		.add(Attributes.ATTACK_KNOCKBACK, 1.5f)
	    		.add(Attributes.MOVEMENT_SPEED, 0.6f)
	    		.add(Attributes.FOLLOW_RANGE, 20.0f);
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
				
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 0.8D, true));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 1.2f));
		this.goalSelector.addGoal(5, new TemptGoal(this, 0.7f, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 0.65D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(7, new HissGoal(this, Player.class, 5.0f, 0.005f));
		this.goalSelector.addGoal(8, new FollowParentGoal(this, 0.55f));
		this.goalSelector.addGoal(9, new BreedGoal(this, 0.5f));
		this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 0.6f));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
		
		this.targetSelector.addGoal(0, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
	    this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
	    this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
	    this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true, PREY_SELECTOR));
	    this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));
	}
	
	@Override
    public InteractionResult mobInteract(Player player, InteractionHand hand)
	{
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        ItemStack[] itemForTaming = TEMPTATION_ITEMS.getItems();
        
        if (isFood(itemstack))
        {
			return super.mobInteract(player, hand);
		}
        
        for (int i = 0; i < itemForTaming.length; i++)
        {
        	if (item.equals(itemForTaming[i].getItem()) && !isTame())
            {
                if (this.level.isClientSide)
                {
                    return InteractionResult.CONSUME;
                }
                else
                {
                    if (!player.getAbilities().instabuild)
                    {
                        itemstack.shrink(1);
                    }

                    if (!ForgeEventFactory.onAnimalTame(this, player))
                    {
                        if (!this.level.isClientSide)
                        {
                            super.tame(player);
                            this.navigation.recomputePath();
                            this.setTarget(null);
                            this.level.broadcastEntityEvent(this, (byte)7);
                            setSitting(true);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
		}
        
        if(isTame() && !this.level.isClientSide && hand == InteractionHand.MAIN_HAND)
        {
            setSitting(!isSitting());
            return InteractionResult.SUCCESS;
        }
        
        for (int i = 0; i < itemForTaming.length; i++)
        {
        	if (itemstack.getItem().equals(itemForTaming[i].getItem()))
            {
                return InteractionResult.PASS;
            }
        }

        return super.mobInteract(player, hand);
    }
	
	protected void defineSynchedData()
	{
	    super.defineSynchedData();
	    this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
	    this.entityData.define(SITTING, false);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag)
	{
		super.readAdditionalSaveData(tag);
		setSitting(tag.getBoolean("isSitting"));
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag)
	{
		super.addAdditionalSaveData(tag);
		tag.putBoolean("isSitting", this.isSitting());
	}
	
	public void setSitting(boolean sitting)
	{
		this.entityData.set(SITTING, sitting);
		this.setOrderedToSit(sitting);
	}
	
	public boolean isSitting()
	{
		return this.entityData.get(SITTING);
	}
	
	@Override
	public Team getTeam()
	{
		return super.getTeam();
	}
	
	public boolean canBeLeashed(Player player)
	{
		return true;
	}
	
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent)
	{
		EntityInit.BIG_FLOPPA.get().create(level);
		BigFloppaEntity bigFloppaEntity = (BigFloppaEntity) EntityInit.BIG_FLOPPA.get().create(level);
		
	    UUID uuid = this.getOwnerUUID();
	    
	      if (uuid != null)
	      {
	    	bigFloppaEntity.setOwnerUUID(uuid);
			bigFloppaEntity.setTame(true);
	      }
	      return bigFloppaEntity;
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		if (this.isAngry())
		{
			return SoundInit.FLOPPA_GROWL.get();
	    }
		else
		{
	        return SoundInit.KARR_1.get();
		}
	}
	
	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	{
		return SoundEvents.CAT_HURT;
	}

   protected SoundEvent getDeathSound()
   {
	    return SoundEvents.CAT_DEATH;
   }
   
   @Override
   public boolean isAngry()
   {
	   if(this.entityData.get(DATA_REMAINING_ANGER_TIME) < 0)
	   {
		   System.out.println("INCAZZATO");
		   return true;
	   }
	   else
	   {
		   return false;
	   }
   }
   
   @Override
   public void setTame(boolean tamed)
   {
      super.setTame(tamed);
      if (tamed)
      {
         getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
         setHealth(20.0F);
      }
      else
      {
         getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
      }

      getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
   }
   
   	@Override
	public boolean isFood(ItemStack stack)
	{
		return stack.getItem() == ItemInit.SHRIMP.get();
	}
   	
   	public void start()
	{
        BigFloppaEntity.this.setTarget((LivingEntity)null);
    }

    public void tick()
    {
    	BigFloppaEntity.this.setTarget((LivingEntity)null);
        super.tick();
    }
	
    @Override
    public Packet<?> getAddEntityPacket()
    {
    	return NetworkHooks.getEntitySpawningPacket(this);
    }
	
	@Override
	public int getRemainingPersistentAngerTime()
	{
		return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int p_21673_)
	{
		this.entityData.set(DATA_REMAINING_ANGER_TIME, p_21673_);
	}

	@Override
	public UUID getPersistentAngerTarget()
	{
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid)
	{
		this.persistentAngerTarget = uuid;
	}

	@Override
	public void startPersistentAngerTimer()
	{
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}
	
	/*@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand)
	{
	      ItemStack itemstack = player.getItemInHand(hand);
	      Item item = itemstack.getItem();
	      if (this.level.isClientSide)
	      {
	         boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.SALMON && !this.isTame(); //&& !this.isAngry();
	         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
	      }
	      else
	      {
	         if (this.isTame())
	         {
	            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth())
	            {
	               if (!player.getAbilities().instabuild)
	               {
	                  itemstack.shrink(1);
	               }

	               this.heal((float)item.getFoodProperties().getNutrition());
	               return InteractionResult.SUCCESS;
	            }

	            if (!(item instanceof DyeItem) || !(item == ItemInit.CEMENT_BAG.get()) )
	            {
	            	InteractionResult actionresulttype = super.mobInteract(player, hand);
	               if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(player))
	               {
	                  this.setOrderedToSit(!this.isOrderedToSit());
	                  this.jumping = false;
	                  this.navigation.stop();
	                  this.setTarget((LivingEntity)null);
	                  return InteractionResult.SUCCESS;
	               }

	               return actionresulttype;
	            }

               return InteractionResult.SUCCESS;
            }
         
	         
	         if (item.equals(Items.COOKED_BEEF) || item.equals(Items.RABBIT) || item.equals(Items.COOKED_RABBIT) || item.equals(ItemInit.SHRIMP.get()) || item.equals(ItemInit.CEMENT_BAG.get()) && !this.isAngry())
         	 {
	            if (!player.getAbilities().instabuild)
	            {
	               itemstack.shrink(1);
	            }

	            if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
	            {
	               this.tame(player);
	               this.navigation.stop();
	               this.setTarget((LivingEntity)null);
	               this.setOrderedToSit(true);
	               this.level.broadcastEntityEvent(this, (byte)7);
	            }
	            else
	            {
	               this.level.broadcastEntityEvent(this, (byte)6);
	            }

	            return InteractionResult.SUCCESS;
	         }
	         else if(this.isTame() && item.equals(ItemInit.CEMENT_BAG.get()) && this.getAttributeValue(Attributes.MAX_HEALTH) != 30.0f)
	         {
	        	 itemstack.shrink(1);
	        	 this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
	             this.setHealth(30.0F);
	             this.level.addParticle(ParticleTypes.SPLASH, this.getX(), this.getY(), this.getZ(), this.getX(), this.getY(), this.getZ());
	         }

	         return super.mobInteract(player, hand);
	      }
	}*/
}