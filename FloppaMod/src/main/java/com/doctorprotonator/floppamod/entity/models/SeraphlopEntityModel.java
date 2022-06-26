package com.doctorprotonator.floppamod.entity.models;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.SeraphlopEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SeraphlopEntityModel extends AnimatedGeoModel<SeraphlopEntity>
{
	@Override
	public ResourceLocation getModelLocation(SeraphlopEntity object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "geo/seraphlop.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SeraphlopEntity object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/seraphlop.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(SeraphlopEntity animatable)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "animations/seraphlop.animation.json");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setLivingAnimations(SeraphlopEntity entity, Integer uniqueID, AnimationEvent customPredicate)
	{
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone root = this.getAnimationProcessor().getBone("Root");
		
		
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		
		if(root != null)
		{
			root.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			root.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}