package com.doctorprotonator.floppamod.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SeraphlopObelisk extends AmbientCreature implements IAnimatable
{
	private AnimationFactory factory = new AnimationFactory(this);
	
	public SeraphlopObelisk(EntityType<? extends AmbientCreature> entityType, Level level)
	{
		super(entityType, level);
	}
	
	public static AttributeSupplier.Builder createAttributes()
	{
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 50.0f);
	}

	@Override
	protected void defineSynchedData()
	{
		
	}

	@Override
	public Packet<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	//IAnimatable
	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController<SeraphlopObelisk>(this, "controller", 0, this::predicate));
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seraphlop_obelisk.floating"));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() 
	{
		return this.factory;
	}
}