package com.doctorprotonator.floppamod.entity.renderers;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.SeraphlopEntity;
import com.doctorprotonator.floppamod.entity.models.SeraphlopEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SeraphlopEntityRenderer extends GeoEntityRenderer<SeraphlopEntity>
{
	public SeraphlopEntityRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new SeraphlopEntityModel());
		this.shadowRadius = 2.4f;
	}
	
	@Override
	public ResourceLocation getTextureLocation(SeraphlopEntity instance)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/seraphlop.png");
	}
	
	@Override
	public RenderType getRenderType(SeraphlopEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation)
	{
		stack.scale(6.5f, 6.5f, 6.5f);
		return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
	}
}