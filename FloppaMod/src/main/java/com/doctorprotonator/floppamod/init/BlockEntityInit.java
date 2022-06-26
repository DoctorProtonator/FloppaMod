package com.doctorprotonator.floppamod.init;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.block.block_entities.SeraphlopStatueEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FloppaMod.MOD_ID);
	
	public static void register(IEventBus eventBus)
	{
		BLOCK_ENTITIES.register(eventBus);
	}
	
	// SERAPHLOP STATUE
	public static final RegistryObject<BlockEntityType<SeraphlopStatueEntity>> SERAPHLOP_STATUE_ENTITY =
			BLOCK_ENTITIES.register("seraphlop_statue_entity", () ->
			BlockEntityType.Builder.of(SeraphlopStatueEntity::new,
					BlockInit.SERAPHLOP_STATUE.get()).build(null));
}