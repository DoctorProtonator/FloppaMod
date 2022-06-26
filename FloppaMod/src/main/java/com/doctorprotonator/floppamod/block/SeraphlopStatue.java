package com.doctorprotonator.floppamod.block;

import com.doctorprotonator.floppamod.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SeraphlopStatue extends DirectionalBlock implements EntityBlock
{
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("clicked");
	
	public SeraphlopStatue(Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return BlockEntityInit.SERAPHLOP_STATUE_ENTITY.get().create(blockPos, blockState);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState blockState)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}
	
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
		builder.add(ACTIVATED);
	}
	
	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player,
			InteractionHand hand, BlockHitResult blockHitRes)
	{
		if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND)
		{
			ItemStack itemstack = player.getItemInHand(hand);
		    if(itemstack.is(Items.BLAZE_ROD))
		    {
		    	boolean currentState = blockState.getValue(ACTIVATED);
				level.setBlock(blockPos, blockState.setValue(ACTIVATED, !currentState), 3);
		    }
		}
		return InteractionResult.SUCCESS;
	}
	
	/*
	private void spawnBossAndBreakBlock()
	{
		SeraphlopEntity boss = new SeraphlopEntity(EntityInit.SERAPHLOP.get(), this.level);
		double x = blockPos.getX();
		double y = blockPos.getY();
		double z = blockPos.getZ();
		
		this.level.addFreshEntity(boss);
		boss.setAggressive(true);
		boss.setPosRaw(x, y + 3, z);
	}*/
}