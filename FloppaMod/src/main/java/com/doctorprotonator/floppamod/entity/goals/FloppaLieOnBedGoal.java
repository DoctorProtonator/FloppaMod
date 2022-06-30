package com.doctorprotonator.floppamod.entity.goals;

import java.util.EnumSet;

import com.doctorprotonator.floppamod.entity.BigFloppaEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

public class FloppaLieOnBedGoal extends MoveToBlockGoal
{
	private final BigFloppaEntity floppa;

	   public FloppaLieOnBedGoal(BigFloppaEntity floppa, double p_25136_, int p_25137_)
	   {
	      super(floppa, p_25136_, p_25137_, 6);
	      this.floppa = floppa;
	      this.verticalSearchStart = -2;
	      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
	   }

	   public boolean canUse()
	   {
	      return this.floppa.isTame() && !this.floppa.isOrderedToSit() && !this.floppa.isLying() && super.canUse();
	   }

	   public void start()
	   {
	      super.start();
	      this.floppa.setInSittingPose(false);
	   }

	   protected int nextStartTick(PathfinderMob p_25140_)
	   {
	      return 40;
	   }

	   public void stop()
	   {
	      super.stop();
	      this.floppa.setLying(false);
	   }

	   public void tick()
	   {
	      super.tick();
	      this.floppa.setInSittingPose(false);
	      
	      if (!this.isReachedTarget())
	      {
	         this.floppa.setLying(false);
	      }
	      else if(!this.floppa.isLying())
	      {
	         this.floppa.setLying(true);
	      }
	   }

	   protected boolean isValidTarget(LevelReader p_25142_, BlockPos p_25143_)
	   {
	      return p_25142_.isEmptyBlock(p_25143_.above()) && p_25142_.getBlockState(p_25143_).is(BlockTags.BEDS);
	   }
}