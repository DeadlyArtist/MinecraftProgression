package com.deadlyartist.minecraftprogression.init;
import java.util.function.Supplier;

import com.deadlyartist.minecraftprogression.Progression;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Progression.MODID);
	
	// Steel
	public static final RegistryObject<Item> STEEL_INGOT = register("steel_ingot", () -> new Item(new Item.Properties()
			.tab(Progression.MAIN_TAB)));
	public static final RegistryObject<Item> STEEL_BINDING = register("steel_binding", () -> new Item(new Item.Properties()
			.tab(Progression.MAIN_TAB)));
	//public static final RegistryObject<Item> STEEL_FRAME = register("steel_frame", () -> new Item(new Item.Properties()
	//		.tab(Progression.MAIN_TAB)));
	
	// Steelbound
	public static final RegistryObject<Item> STEELBOUND_IRON_AXE = register("steelbound_iron_axe", () -> new AxeItem(ModTiers.STEELBOUND_IRON, 6.0F, -3.1F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_BOOTS = register("steelbound_iron_boots", () -> new ArmorItem(ModArmorMaterials.STEELBOUND_IRON, EquipmentSlot.FEET, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_CHESTPLATE = register("steelbound_iron_chestplate", () -> new ArmorItem(ModArmorMaterials.STEELBOUND_IRON, EquipmentSlot.CHEST, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_HELMET = register("steelbound_iron_helmet", () -> new ArmorItem(ModArmorMaterials.STEELBOUND_IRON, EquipmentSlot.HEAD, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_HOE = register("steelbound_iron_hoe", () -> new HoeItem(ModTiers.STEELBOUND_IRON, -2, -1.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_LEGGINGS = register("steelbound_iron_leggings", () -> new ArmorItem(ModArmorMaterials.STEELBOUND_IRON, EquipmentSlot.LEGS, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_PICKAXE = register("steelbound_iron_pickaxe", () -> new PickaxeItem(ModTiers.STEELBOUND_IRON, 1, -2.8F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_SHOVEL = register("steelbound_iron_shovel", () -> new ShovelItem(ModTiers.STEELBOUND_IRON, 1.5F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> STEELBOUND_IRON_SWORD = register("steelbound_iron_sword", () -> new SwordItem(ModTiers.STEELBOUND_IRON, 3, -2.4F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	
	// Diamond
	public static final RegistryObject<Item> DIAMOND_HEART = register("diamond_heart", () -> new Item(new Item.Properties()
			.tab(Progression.MAIN_TAB)));
	
	public static final RegistryObject<Item> DIAMOND_AXE = register("diamond_axe", () -> new AxeItem(ModTiers.DIAMOND, 5.0F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_BOOTS = register("diamond_boots", () -> new ArmorItem(ModArmorMaterials.DIAMOND, EquipmentSlot.FEET, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_CHESTPLATE = register("diamond_chestplate", () -> new ArmorItem(ModArmorMaterials.DIAMOND, EquipmentSlot.CHEST, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_HELMET = register("diamond_helmet", () -> new ArmorItem(ModArmorMaterials.DIAMOND, EquipmentSlot.HEAD, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_HOE = register("diamond_hoe", () -> new HoeItem(ModTiers.DIAMOND, -3, 0.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_LEGGINGS = register("diamond_leggings", () -> new ArmorItem(ModArmorMaterials.DIAMOND, EquipmentSlot.LEGS, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_PICKAXE = register("diamond_pickaxe", () -> new PickaxeItem(ModTiers.DIAMOND, 1, -2.8F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_SHOVEL = register("diamond_shovel", () -> new ShovelItem(ModTiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> DIAMOND_SWORD = register("diamond_sword", () -> new SwordItem(ModTiers.DIAMOND, 3, -2.4F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	
	// Netherite
	public static final RegistryObject<Item> NETHERITE_AXE = register("netherite_axe", () -> new AxeItem(ModTiers.NETHERITE, 5.0F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_BOOTS = register("netherite_boots", () -> new ArmorItem(ModArmorMaterials.NETHERITE, EquipmentSlot.FEET, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_CHESTPLATE = register("netherite_chestplate", () -> new ArmorItem(ModArmorMaterials.NETHERITE, EquipmentSlot.CHEST, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_HELMET = register("netherite_helmet", () -> new ArmorItem(ModArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_HOE = register("netherite_hoe", () -> new HoeItem(ModTiers.NETHERITE, -4, 0.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_LEGGINGS = register("netherite_leggings", () -> new ArmorItem(ModArmorMaterials.NETHERITE, EquipmentSlot.LEGS, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_PICKAXE = register("netherite_pickaxe", () -> new PickaxeItem(ModTiers.NETHERITE, 1, -2.8F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_SHOVEL = register("netherite_shovel", () -> new ShovelItem(ModTiers.NETHERITE, 1.5F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> NETHERITE_SWORD = register("netherite_sword", () -> new SwordItem(ModTiers.NETHERITE, 3, -2.4F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	
	// Gold
	public static final RegistryObject<Item> GOLD_AXE = register("gold_axe", () -> new AxeItem(ModTiers.GOLD, 6.0F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_BOOTS = register("gold_boots", () -> new ArmorItem(ModArmorMaterials.GOLD, EquipmentSlot.FEET, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_CHESTPLATE = register("gold_chestplate", () -> new ArmorItem(ModArmorMaterials.GOLD, EquipmentSlot.CHEST, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_HELMET = register("gold_helmet", () -> new ArmorItem(ModArmorMaterials.GOLD, EquipmentSlot.HEAD, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_HOE = register("gold_hoe", () -> new HoeItem(ModTiers.GOLD, 0, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_LEGGINGS = register("gold_leggings", () -> new ArmorItem(ModArmorMaterials.GOLD, EquipmentSlot.LEGS, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_PICKAXE = register("gold_pickaxe", () -> new PickaxeItem(ModTiers.GOLD, 1, -2.8F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_SHOVEL = register("gold_shovel", () -> new ShovelItem(ModTiers.GOLD, 1.5F, -3.0F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	public static final RegistryObject<Item> GOLD_SWORD = register("gold_sword", () -> new SwordItem(ModTiers.GOLD, 3, -2.4F, new Item.Properties()
			.tab(Progression.MAIN_TAB).durability(0)));
	
	private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
		return ITEMS.register(name, item);
	}
}
