package com.doctorprotonator.floppamod.events;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.BigFloppaEntity;
import com.doctorprotonator.floppamod.entity.SeraphlopEntity;
import com.doctorprotonator.floppamod.init.EntityInit;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid= FloppaMod.MOD_ID, bus = Bus.MOD)
public class ModEvents
{
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event)
	{
		event.put(EntityInit.BIG_FLOPPA.get(), BigFloppaEntity.createAttributes().build());
		event.put(EntityInit.SERAPHLOP.get(), SeraphlopEntity.createAttributes().build());
	}
}