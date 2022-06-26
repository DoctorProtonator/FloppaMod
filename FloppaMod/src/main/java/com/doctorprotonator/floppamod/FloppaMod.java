package com.doctorprotonator.floppamod;

import java.util.logging.LogManager;

import org.apache.logging.log4j.Logger;

import com.doctorprotonator.floppamod.init.BlockEntityInit;
import com.doctorprotonator.floppamod.init.BlockInit;
import com.doctorprotonator.floppamod.init.EntityInit;
import com.doctorprotonator.floppamod.init.ItemInit;
import com.doctorprotonator.floppamod.init.SoundInit;
import com.doctorprotonator.floppamod.init.StructuresInit;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod("floppamod")
public class FloppaMod
{
	public static final String MOD_ID = "floppamod";
	
	public static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger();
	
	public static final CreativeModeTab FLOPPAMOD_TAB = new CreativeModeTab(MOD_ID)
	{
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon()
		{
			return new ItemStack(ItemInit.SHRIMP.get());
		}
	};
	
	public FloppaMod()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		SoundInit.SOUNDS.register(bus);
		BlockEntityInit.BLOCK_ENTITIES.register(bus);
		EntityInit.ENTITIES.register(bus);
		StructuresInit.DEFERRED_REGISTRY_STRUCTURE.register(bus);
		
		GeckoLib.initialize();
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() ->
			{
				SpawnPlacements.register(EntityInit.BIG_FLOPPA.get(),
						SpawnPlacements.Type.ON_GROUND,
						Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
						Animal::checkAnimalSpawnRules);
			}
		);
	}
}