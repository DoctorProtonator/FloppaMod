package com.doctorprotonator.floppamod.block.block_entities;

import com.doctorprotonator.floppamod.entity.SeraphlopEntity;
import com.doctorprotonator.floppamod.init.BlockEntityInit;
import com.doctorprotonator.floppamod.init.EntityInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
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
		nbt.putBoolean("bossSpawned", bossSpawned);
	}
	
	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		nbt.getBoolean("bossSpawned");
	}

	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController<SeraphlopStatueEntity>
		(this, "controller", 0, this::predicate));
	}
	
	private <E extends SeraphlopStatueEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(!isActivated)
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seraphlop_statue.idle", false));
        }
        else
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.seraphlop_statue.awakening", false));
 
            if(event.getAnimationTick() == eventKeyframe)
            {
                final LocalPlayer player = Minecraft.getInstance().player;
                if (player != null)
                {
                    player.displayClientMessage(new TextComponent("DO NOT BE AFRAID."), true);
                }
            }
        }
        return PlayState.CONTINUE;
    }
	
	private void spawnBoss()
	{
		SeraphlopEntity boss = new SeraphlopEntity(EntityInit.SERAPHLOP.get(), this.level);
		double x = blockPos.getX();
		double y = blockPos.getY();
		double z = blockPos.getZ();
		
		this.level.addFreshEntity(boss);
		boss.setAggressive(true);
		boss.setPosRaw(x, y + 3, z);
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	public static void tick()
	{
		
	}
}