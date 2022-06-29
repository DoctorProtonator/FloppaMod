package com.doctorprotonator.floppamod.entity.models;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.SeraphlopObelisk;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SeraphlopObeliskModel extends AnimatedGeoModel<SeraphlopObelisk>
{
	@Override
	public ResourceLocation getModelLocation(SeraphlopObelisk object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "geo/seraphlop_obelisk.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(SeraphlopObelisk object)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/seraphlop_obelisk.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(SeraphlopObelisk animatable)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "animations/seraphlop_obelisk.animation.json");
	}
}