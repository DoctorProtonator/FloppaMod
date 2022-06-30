package com.doctorprotonator.floppamod.entity.goals;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.doctorprotonator.floppamod.init.SoundInit;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class HissGoal extends Goal
{
   public static final float DEFAULT_PROBABILITY = 0.02F;
   protected final Mob mob;
   @Nullable
   protected Entity lookAt;
   protected final float lookDistance;
   private int lookTime;
   protected final float probability;
   private final boolean onlyHorizontal;
   protected final Class<? extends LivingEntity> lookAtType;
   protected final TargetingConditions lookAtContext;
   
   public static boolean isHissing = false;

   public HissGoal(Mob floppa, Class<? extends LivingEntity> lookAtType, float lookDistance)
   {
      this(floppa, lookAtType, lookDistance, DEFAULT_PROBABILITY);
   }

   public HissGoal(Mob floppa, Class<? extends LivingEntity> lookAtType, float lookDistance, float prob)
   {
      this(floppa, lookAtType, lookDistance, prob, false);
   }

   public HissGoal(Mob floppa, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability, boolean onlyHorizontal)
   {
      this.mob = floppa;
      this.lookAtType = lookAtType;
      this.lookDistance = lookDistance;
      this.probability = probability;
      this.onlyHorizontal = onlyHorizontal;
      this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      if (lookAtType == Player.class)
      {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDistance).selector((p_25531_) -> {
            return EntitySelector.notRiding(floppa).test(p_25531_);
         });
      }
      else
      {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDistance);
      }
   }

   public boolean canUse()
   {
	  if(this.mob.getRandom().nextFloat() >= this.probability)
	  {
		  return false;
	  }
      else
      {
         if(this.mob.getTarget() != null)
         {
            this.lookAt = this.mob.getTarget();
         }

         if(this.lookAtType == Player.class)
         {
            this.lookAt = this.mob.level.getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
         }
         else
         {
            this.lookAt = this.mob.level.getNearestEntity(this.lookAtType, this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.mob.getBoundingBox().inflate((double)this.lookDistance, 3.0D, (double)this.lookDistance));
         }
         return this.lookAt != null;
      }
   }

   public boolean canContinueToUse()
   {
	   if (!this.lookAt.isAlive())
       {
          return false;
       }
       else if (this.mob.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance))
       {
          return false;
       }
       else
       {
          return this.lookTime > 0;
       }
   }

   public void start()
   {
	   this.mob.getNavigation().stop();
       this.lookTime = 45;
       isHissing = true;
       mob.playSound(getHissSound(), 0.45f, 1.0f);
   }
   
   @Override
   public void stop()
   {
      this.lookAt = null;
      isHissing = false;
	  this.mob.getNavigation().tick();
   }
   
   @Override
   public void tick()
   {
      this.mob.getLookControl().setLookAt(this.lookAt.getX(), this.lookAt.getEyeY(), this.lookAt.getZ());
      --this.lookTime;
   }

   private SoundEvent getHissSound()
   {
	   return SoundInit.CHHH.get();
   }
}