package com.doctorprotonator.floppamod.item.models;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.block.block_entities.SeraphlopStatueEntity;
import com.doctorprotonator.floppamod.item.SeraphlopStatueItem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SeraphlopStatueItemModel extends AnimatedGeoModel<SeraphlopStatueItem>
{
	@Override
	public ResourceLocation getAnimationFileLocation(SeraphlopStatueItem animatable)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "animations/seraphlop_statue.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SeraphlopStatueItem object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "geo/seraphlop_statue.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SeraphlopStatueItem object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/item/seraphlop_statue.png");
	}
}