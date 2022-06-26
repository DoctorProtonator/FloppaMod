package com.doctorprotonator.floppamod.init;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.entity.BigFloppaEntity;
import com.doctorprotonator.floppamod.entity.SeraphlopEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class EntityInit
{
	private EntityInit() {}
	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
			ForgeRegistries.ENTITIES, FloppaMod.MOD_ID);
	
	public static final RegistryObject<EntityType<BigFloppaEntity>> BIG_FLOPPA =
			ENTITIES.register("big_floppa", () -> EntityType.Builder
			.of(BigFloppaEntity::new, MobCategory.CREATURE)
			.sized(1.15f, 1.25f)
			.build(new ResourceLocation(FloppaMod.MOD_ID, "big_floppa").toString()));
	
	public static final RegistryObject<EntityType<SeraphlopEntity>> SERAPHLOP =
			ENTITIES.register("seraphlop", () -> EntityType.Builder
			.of(SeraphlopEntity::new, MobCategory.MONSTER)
			.sized(2.3f, 3.5f)
			.build(new ResourceLocation(FloppaMod.MOD_ID, "seraphlop").toString()));
}