package com.doctorprotonator.floppamod.world.gen;

import com.doctorprotonator.floppamod.FloppaMod;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FloppaMod.MOD_ID)
public class ModWorldEvents
{
	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event)
	{
		ModEntityGeneration.onEntitySpawn(event);
	}
}