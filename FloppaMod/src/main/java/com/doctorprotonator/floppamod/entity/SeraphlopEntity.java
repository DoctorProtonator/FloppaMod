package com.doctorprotonator.floppamod.entity;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.doctorprotonator.floppamod.entity.goals.AlwaysLookAtPlayerGoal;
import com.doctorprotonator.floppamod.init.EntityInit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SeraphlopEntity extends FlyingMob implements IAnimatable, RangedAttackMob, Enemy
{
	private AnimationFactory factory = new AnimationFactory(this);
	private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
	private static final EntityDataAccessor<Integer> DATA_ID_INV = SynchedEntityData.defineId(SeraphlopEntity.class, EntityDataSerializers.INT);
	private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (entity) ->
		{ return entity.getMobType() != MobType.UNDEAD && entity.getMobType() != MobType.ARTHROPOD && entity.getMobType() != MobType.ILLAGER
		&& entity.getMobType() != MobType.WATER && entity.getType() != EntityInit.BIG_FLOPPA.get() && entity.attackable(); };
	
	public SeraphlopEntity(EntityType<? extends FlyingMob> seraphlop, Level level)
	{
		super(seraphlop, level);
	}
	
	public static AttributeSupplier.Builder createAttributes()
	{
		return Monster.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 500.0d)
				.add(Attributes.MOVEMENT_SPEED, 0.8d)
				.add(Attributes.FLYING_SPEED, 0.8d)
	    		.add(Attributes.FOLLOW_RANGE, 80.0d);
	}
	
	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new AlwaysLookAtPlayerGoal(this, Player.class, 50.0F, false));
		//this.goalSelector.addGoal(1, new RandomFloatAroundGoal(this));
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 4, 5, 50)); //rangedAttackMob, speedModifier, attackIntervalMin, attackIntervalMax, attackRadius
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
	}

	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController<SeraphlopEntity>(this, "controller", 0, this::predicate));
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		if(event.isMoving())
		{
			event.getController().setAnimation(new AnimationBuilder().addAnimation("flying.seraphlop.anim", true));
			//event.getController().setAnimationSpeed(2.7f);
			return PlayState.CONTINUE;
		}
		
		event.getController().setAnimation(new AnimationBuilder().addAnimation("flying.seraphlop.anim", true));
		//event.getController().setAnimationSpeed(1f);
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}
	
	/*static class RandomFloatAroundGoal extends Goal
	{
		private final SeraphlopEntity seraphlop;
	
		public RandomFloatAroundGoal(SeraphlopEntity seraphlop)
		{
			this.seraphlop = seraphlop;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}
	
		public boolean canUse()
		{
			MoveControl movecontrol = this.seraphlop.getMoveControl();
			if (!movecontrol.hasWanted())
			{
				return true;
			}
			else
			{
				double d0 = movecontrol.getWantedX() - this.seraphlop.getX();
				double d1 = movecontrol.getWantedY() - this.seraphlop.getY();
				double d2 = movecontrol.getWantedZ() - this.seraphlop.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}
	
		public boolean canContinueToUse()
		{
			return false;
		}
	
		public void start()
		{
		    Random random = this.seraphlop.getRandom();
		    double d0 = this.seraphlop.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
		    double d1 = this.seraphlop.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
		    double d2 = this.seraphlop.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
		    this.seraphlop.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
		}
	}*/
	
	/*static class SeraphlopMoveControl extends MoveControl
	{
	      private final SeraphlopEntity seraphlop;
	      private int floatDuration;

	      public SeraphlopMoveControl(SeraphlopEntity seraphlop)
	      {
	         super(seraphlop);
	         this.seraphlop = seraphlop;
	      }

	      public void tick()
	      {
	         if (this.operation == MoveControl.Operation.MOVE_TO)
	         {
	            if (this.floatDuration-- <= 0)
	            {
	               this.floatDuration += this.seraphlop.getRandom().nextInt(5) + 2;
	               Vec3 vec3 = new Vec3(this.wantedX - this.seraphlop.getX(), this.wantedY - this.seraphlop.getY(), this.wantedZ - this.seraphlop.getZ());
	               double d0 = vec3.length();
	               vec3 = vec3.normalize();
	               if (this.canReach(vec3, Mth.ceil(d0)))
	               {
	                  this.seraphlop.setDeltaMovement(this.seraphlop.getDeltaMovement().add(vec3.scale(0.1D)));
	               }
	               else
	               {
	                  this.operation = MoveControl.Operation.WAIT;
	               }
	            }
	         }
	      }

	      private boolean canReach(Vec3 p_32771_, int p_32772_)
	      {
	         AABB aabb = this.seraphlop.getBoundingBox();

	         for(int i = 1; i < p_32772_; ++i)
	         {
	            aabb = aabb.move(p_32771_);
	            if (!this.seraphlop.level.noCollision(this.seraphlop, aabb))
	            {
	               return false;
	            }
	         }
	         return true;
	      }
    }*/
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_)
	{
		super.readAdditionalSaveData(p_21450_);
		
		if(this.hasCustomName())
		{
			this.bossEvent.setName(this.getDisplayName());
	    }
	}
	
	public void setCustomName(@Nullable Component p_31476_)
	{
		super.setCustomName(p_31476_);
		this.bossEvent.setName(this.getDisplayName());
	}

	@Override
	public void performRangedAttack(LivingEntity p_33317_, float p_33318_)
	{
		
	}
	
	@Override
	public void startSeenByPlayer(ServerPlayer player)
	{
		super.startSeenByPlayer(player);
		this.bossEvent.addPlayer(player);
	}
	
	@Override
	public void stopSeenByPlayer(ServerPlayer player)
	{
		super.stopSeenByPlayer(player);
		this.bossEvent.removePlayer(player);
	}
}