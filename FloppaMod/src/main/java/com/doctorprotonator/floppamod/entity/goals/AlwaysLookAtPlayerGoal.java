package com.doctorprotonator.floppamod.entity.goals;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class AlwaysLookAtPlayerGoal extends Goal
{
   protected final Mob mob;
   @Nullable
   protected Entity lookAt;
   protected final float lookDistance;
   private int lookTime;
   private final boolean onlyHorizontal;
   protected final Class<? extends LivingEntity> lookAtType;
   protected final TargetingConditions lookAtContext;

   public AlwaysLookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> lookAtType, float lookDistance, boolean onlyHorizontal)
   {
      this.mob = mob;
      this.lookAtType = lookAtType;
      this.lookDistance = lookDistance;
      this.onlyHorizontal = onlyHorizontal;
      this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      
      if (lookAtType == Player.class)
      {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDistance).selector((entity) ->
         { return EntitySelector.notRiding(mob).test(entity); });
      }
      else
      {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDistance);
      }
   }

   public boolean canUse()
   {
     if (this.mob.getTarget() != null)
     {
        this.lookAt = this.mob.getTarget();
     }

     if (this.lookAtType == Player.class)
     {
        this.lookAt = this.mob.level.getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
     }
     else if(this.lookAtType != Player.class)
     {
        return false;
     }
     return this.lookAt != null;
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
      this.lookTime = this.adjustedTickDelay(40 + this.mob.getRandom().nextInt(40));
   }

   public void stop()
   {
      this.lookAt = null;
   }

   public void tick()
   {
      if (this.lookAt.isAlive())
      {
         double d0 = this.onlyHorizontal ? this.mob.getEyeY() : this.lookAt.getEyeY();
         this.mob.getLookControl().setLookAt(this.lookAt.getX(), d0, this.lookAt.getZ());
         --this.lookTime;
      }
   }
}