package com.doctorprotonator.floppamod.entity.renderers;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.SeraphlopObelisk;
import com.doctorprotonator.floppamod.entity.models.SeraphlopObeliskModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SeraphlopObeliskRenderer extends GeoEntityRenderer<SeraphlopObelisk>
{
	public SeraphlopObeliskRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new SeraphlopObeliskModel());
		this.shadowRadius = 0f;
	}
	
	@Override
	public ResourceLocation getTextureLocation(SeraphlopObelisk instance)
	{
		return new ResourceLocation(FloppaMod.MOD_ID, "textures/entity/seraphlop_obelisk.png");
	}
	
	@Override
	public RenderType getRenderType(SeraphlopObelisk animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation)
	{
		return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn,
				textureLocation);
	}
}