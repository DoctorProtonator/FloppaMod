package com.doctorprotonator.floppamod.block.block_entities.renderers;

import com.doctorprotonator.floppamod.block.block_entities.SeraphlopStatueEntity;
import com.doctorprotonator.floppamod.block.block_entities.models.SeraphlopStatueModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SeraphlopStatueRenderer extends GeoBlockRenderer<SeraphlopStatueEntity>
{
	public SeraphlopStatueRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn)
	{
		super(rendererDispatcherIn, new SeraphlopStatueModel());
	}
	
	@Override
	public RenderType getRenderType(SeraphlopStatueEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation)
	{
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}