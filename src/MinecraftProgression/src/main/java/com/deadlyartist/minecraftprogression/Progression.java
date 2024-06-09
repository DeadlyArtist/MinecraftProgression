package com.deadlyartist.minecraftprogression;

import com.deadlyartist.minecraftprogression.init.BlockInit;
import com.deadlyartist.minecraftprogression.init.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLanguageProvider;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("minecraftprogression")
public class Progression {
	public static final String MODID = "minecraftprogression";
	public static final String MAIN_TAB_ID = MODID + "_0";
	public static final CreativeModeTab MAIN_TAB = new CreativeModeTab(MAIN_TAB_ID) {
		
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(ItemInit.STEEL_INGOT.get());
		}
	};
	
	public Progression() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
}
