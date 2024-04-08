package com.deadlyartist.minecraftprogression.init;
import java.util.function.Supplier;

import com.deadlyartist.minecraftprogression.Progression;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Progression.MODID);
	public static final RegistryObject<Item> INGOT_STEEL = register("steel_ingot", () -> new Item(new Item.Properties()
			.tab(Progression.MAIN_TAB)));
	
	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
		return ITEMS.register(name, item);
	}
}
