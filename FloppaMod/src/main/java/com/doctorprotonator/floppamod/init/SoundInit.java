package com.doctorprotonator.floppamod.init;

import com.doctorprotonator.floppamod.FloppaMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FloppaMod.MOD_ID);
	
	public static final RegistryObject<SoundEvent> KARR_1 = SOUNDS.register("item.karr_1", () -> new SoundEvent(new ResourceLocation(FloppaMod.MOD_ID, "item.karr_1")));
	public static final RegistryObject<SoundEvent> CHHH = SOUNDS.register("item.chhh", () -> new SoundEvent(new ResourceLocation(FloppaMod.MOD_ID, "item.chhh")));
	public static final RegistryObject<SoundEvent> FLOPPA_GROWL = SOUNDS.register("item.floppa_growl", () -> new SoundEvent(new ResourceLocation(FloppaMod.MOD_ID, "item.floppa_growl")));
}