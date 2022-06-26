package com.doctorprotonator.floppamod.init;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.item.SeraphlopStatueItem;
import com.google.common.base.Supplier;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit
{
public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FloppaMod.MOD_ID);
	
	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item)
	{
		return ITEMS.register(name, item);
	}
	
	// Shrimp
	public static final RegistryObject<Item> SHRIMP = ITEMS.register("shrimp", () -> new Item(new Item.Properties()
			.tab(FloppaMod.FLOPPAMOD_TAB)
			.food(new FoodProperties.Builder()
					.fast()
					.nutrition(2)
					.saturationMod(0.1f)
					.build())));
	
	// Cooked Shrimp
	public static final RegistryObject<Item> COOKED_SHRIMP = ITEMS.register("cooked_shrimp", () -> new Item(new Item.Properties()
			.tab(FloppaMod.FLOPPAMOD_TAB)
			.food(new FoodProperties.Builder()
					.fast()
					.nutrition(4)
					.saturationMod(0.5f)
					.build())));
	
	// Cement Bag
	public static final RegistryObject<Item> CEMENT_BAG = ITEMS.register("cement_bag", () -> new Item(new Item.Properties()
			.tab(FloppaMod.FLOPPAMOD_TAB)
			.food(new FoodProperties.Builder()
					.nutrition(1)
					.saturationMod(0.1f)
					.build())));
	
	// Flop
	public static final RegistryObject<Item> FLOP = ITEMS.register("flop", () -> new Item(new Item.Properties()
			.tab(FloppaMod.FLOPPAMOD_TAB)
			.food(new FoodProperties.Builder()
					.nutrition(1)
					.saturationMod(0.1f)
					.build())));
	
	
	// SPAWN EGGS
	
	// Big Floppa
	public static final RegistryObject<Item> BIG_FLOPPA_SPAWN_EGG = ITEMS.register("big_floppa_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.BIG_FLOPPA,0xa3855f, 0x58412b,
                    new Item.Properties().tab(FloppaMod.FLOPPAMOD_TAB)));
	
	//Seraphlop
	public static final RegistryObject<Item> SERAPHLOP_SPAWN_EGG = ITEMS.register("seraphlop_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SERAPHLOP,0xa3855f, 0x58412b,
                    new Item.Properties().tab(FloppaMod.FLOPPAMOD_TAB)));
}