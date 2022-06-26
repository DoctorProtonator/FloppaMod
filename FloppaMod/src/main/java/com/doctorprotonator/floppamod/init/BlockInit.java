package com.doctorprotonator.floppamod.init;

import java.util.function.Function;

import com.doctorprotonator.floppamod.FloppaMod;
import com.doctorprotonator.floppamod.block.SeraphlopStatue;
import com.doctorprotonator.floppamod.item.SeraphlopStatueItem;
import com.google.common.base.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FloppaMod.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;
	
	private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<? extends T> block)
	{
		return BLOCKS.register(name, block);
	}
	
	private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
			Function<RegistryObject<T>, Supplier<? extends Item>> item) 
	{
		RegistryObject<T> obj = registerBlock(name, block);
		ITEMS.register(name, item.apply(obj));
		return obj;
	}
	
	/*EXAMPLE BLOCK
	private static final RegistryObject<Block> EXAMPLE_BLOCK = register("example_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.COLOR_PURPLE)
					.strength(3.0f)
					.sound(SoundType.AMETHYST)
					.requiresCorrectToolForDrops()),
			object -> () -> new BlockItem(object.get(), new Item.Properties().tab(FloppaMod.FLOPPAMOD_TAB)));*/
	
	public static final RegistryObject<Block> SERAPHLOP_STATUE = register("seraphlop_statue",
			() -> new SeraphlopStatue(BlockBehaviour.Properties.of(Material.STONE)
					.noOcclusion()),
			object -> () -> new SeraphlopStatueItem(BlockInit.SERAPHLOP_STATUE.get(), new Item.Properties().tab(FloppaMod.FLOPPAMOD_TAB)));
}