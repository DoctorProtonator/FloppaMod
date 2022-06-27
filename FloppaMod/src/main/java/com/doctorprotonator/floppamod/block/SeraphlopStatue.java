package com.doctorprotonator.floppamod.block;

import com.doctorprotonator.floppamod.block.block_entities.SeraphlopStatueEntity;
import com.doctorprotonator.floppamod.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class SeraphlopStatue extends DirectionalBlock implements EntityBlock
{
	public SeraphlopStatue(Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return BlockEntityInit.SERAPHLOP_STATUE_ENTITY.get().create(blockPos, blockState);
	}
	
	/*@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type)
	{
		return level.isClientSide ? null : (level0, pos, state0, blockEntity) ->
		((SeraphlopStatueEntity)blockEntity).tick();
	}*/
	
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
		    	level.getBlockEntity(blockPos, BlockEntityInit.SERAPHLOP_STATUE_ENTITY.get()).get().isActivated = true;
		    	level.getBlockEntity(blockPos, BlockEntityInit.SERAPHLOP_STATUE_ENTITY.get()).get().setChanged();
		    }
		}
		return InteractionResult.SUCCESS;
	}
}