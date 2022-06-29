package com.doctorprotonator.floppamod.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SeraphlopObelisk extends Entity implements IAnimatable
{
	private AnimationFactory factory = new AnimationFactory(this);
	
	public SeraphlopObelisk(EntityType<? extends Entity> entityType, Level level)
	{
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData()
	{
		
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_)
	{
		
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_)
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