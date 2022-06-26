package com.doctorprotonator.floppamod.entity.renderers;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.BigFloppaEntity;
import com.doctorprotonator.floppamod.entity.models.BigFloppaEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BigFloppaEntityRenderer extends GeoEntityRenderer<BigFloppaEntity>
{
	ResourceLocation FLOPPA_LOCATION = new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/big_floppa.png");
	ResourceLocation ANGRY_LOCATION = new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/angry_floppa.png");
	
	public BigFloppaEntityRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new BigFloppaEntityModel());
		this.shadowRadius = 0.75f;
	}
	
	@Override
	public ResourceLocation getTextureLocation(BigFloppaEntity instance)
	{
		return instance.isAngry() ? ANGRY_LOCATION : FLOPPA_LOCATION;
	}
	
	@Override
	public RenderType getRenderType(BigFloppaEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation)
	{
		if(animatable.isBaby())
		{
			stack.scale(0.45f, 0.45f, 0.45f);
		}
		else
		{
			stack.scale(1.1f, 1.1f, 1.1f);
		}
		
		return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
	}
}