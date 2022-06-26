package com.doctorprotonator.floppamod.entity.models;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.BigFloppaEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class BigFloppaEntityModel extends AnimatedGeoModel<BigFloppaEntity>
{
	@Override
	public ResourceLocation getModelLocation(BigFloppaEntity object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "geo/big_floppa.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(BigFloppaEntity object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/big_floppa.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(BigFloppaEntity animatable)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "animations/big_floppa.animation.json");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setLivingAnimations(BigFloppaEntity entity, Integer uniqueID, AnimationEvent customPredicate)
	{
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("Head");
		
		
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		
		if(head != null)
		{
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}