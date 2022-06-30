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
	
	private static RegistryObject<SoundEvent> registerSound(String name)
	{
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(FloppaMod.MOD_ID, name)));
	}
	
	public static final RegistryObject<SoundEvent> KARR_1 = registerSound("karr_1");
	public static final RegistryObject<SoundEvent> CHHH = registerSound("chhh");
	public static final RegistryObject<SoundEvent> FLOPPA_GROWL = registerSound("floppa_growl");
}