package com.doctorprotonator.floppamod.block.block_entities.models;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.block.block_entities.SeraphlopStatueEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SeraphlopStatueModel extends AnimatedGeoModel<SeraphlopStatueEntity>
{
	@Override
	public ResourceLocation getAnimationFileLocation(SeraphlopStatueEntity animatable)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "animations/seraphlop_statue.animation.json");
		
	}

	@Override
	public ResourceLocation getModelLocation(SeraphlopStatueEntity object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "geo/seraphlop_statue.geo.json");
		
	}

	@Override
	public ResourceLocation getTextureLocation(SeraphlopStatueEntity object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/block/seraphlop_statue.png");
	}
}