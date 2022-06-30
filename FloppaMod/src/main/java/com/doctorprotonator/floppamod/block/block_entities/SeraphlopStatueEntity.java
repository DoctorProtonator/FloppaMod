package com.doctorprotonator.floppamod.block.block_entities;

import com.doctorprotonator.floppamod.entity.SeraphlopEntity;
import com.doctorprotonator.floppamod.init.BlockEntityInit;
import com.doctorprotonator.floppamod.init.EntityInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SeraphlopStatueEntity extends BlockEntity implements IAnimatable
{
	BlockPos blockPos;
	BlockState blockState;
	double eventKeyframe = 360d;
	int counter = 0;
	
	public boolean isActivated = false;
	private boolean bossSpawned = false;
	
	private AnimationFactory factory = new AnimationFactory(this);
	
	public SeraphlopStatueEntity(BlockPos blockPos, BlockState blockState)
	{
		super(BlockEntityInit.SERAPHLOP_STATUE_ENTITY.get(), blockPos, blockState);
		this.blockPos = blockPos;
		this.blockState = blockState;
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.putBoolean("isActivated", isActivated);
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		nbt.getBoolean("isActivated");
	}

	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController<SeraphlopStatueEntity>
		(this, "controller", 0, this::predicate));
	}
	
	private <E extends SeraphlopStatueEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(isActivated == true && bossSpawned == false)
        {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seraphlop_statue.awakening", false));
        	return PlayState.CONTINUE;
        }
        else
        {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seraphlop_statue.idle", true));
        	return PlayState.CONTINUE;
        }
    }
	
	private void spawnBoss()
	{
		SeraphlopEntity boss = new SeraphlopEntity(EntityInit.SERAPHLOP.get(), this.level);
		double x = blockPos.getX();
		double y = blockPos.getY();
		double z = blockPos.getZ();
		
		boss.setAggressive(true);
		boss.setPosRaw(x, y + 3, z);
		this.level.addFreshEntity(boss);
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	public void tick()
	{
		if(isActivated == true && bossSpawned == false)
		{
			if(counter < 360)
			{
				counter++;
			}
			else
			{
				spawnBoss();
				bossSpawned = true;
				setChanged();
			}
		}
	}
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
		saveAdditional(getTileData());
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
	{
		load(pkt.getTag());
	}
}