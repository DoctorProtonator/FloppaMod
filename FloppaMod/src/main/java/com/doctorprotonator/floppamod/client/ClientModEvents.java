package com.doctorprotonator.floppamod.client;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.block.block_entities.renderers.SeraphlopStatueRenderer;
import com.doctorprotonator.floppamod.entity.renderers.BigFloppaEntityRenderer;
import com.doctorprotonator.floppamod.entity.renderers.SeraphlopEntityRenderer;
import com.doctorprotonator.floppamod.entity.renderers.SeraphlopObeliskRenderer;
import com.doctorprotonator.floppamod.init.BlockEntityInit;
import com.doctorprotonator.floppamod.init.EntityInit;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = FloppaMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents
{
	private ClientModEvents() {}
	
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerEntityRenderer(EntityInit.BIG_FLOPPA.get(), BigFloppaEntityRenderer::new);
		event.registerEntityRenderer(EntityInit.SERAPHLOP.get(), SeraphlopEntityRenderer::new);
		event.registerEntityRenderer(EntityInit.SERAPHLOP_OBELISK.get(), SeraphlopObeliskRenderer::new);
		event.registerBlockEntityRenderer(BlockEntityInit.SERAPHLOP_STATUE_ENTITY.get(), SeraphlopStatueRenderer::new);
	}
}