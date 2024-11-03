package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.itemOrBlock.custom.TieredBowItem;
import com.prog.itemOrBlock.tiers.*;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PItems {
    public static class ItemData {
        public String name;
        public Consumer<ItemModelGenerator> modelSupplier;
        public List<TagKey<Item>> tags;

        public ItemData(String name, Consumer<ItemModelGenerator> modelSupplier, @Nullable List<TagKey<Item>> tags) {
            this.name = name;
            this.modelSupplier = modelSupplier;
            this.tags = tags == null ? List.of() : tags;
        }
    }

    public static final Map<Item, ItemData> data = new HashMap<>();

    // Misc
    public static final Item MACHINE_CIRCUIT = register("MACHINE_CIRCUIT", new Item(new FabricItemSettings().group(ItemGroup.REDSTONE)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item TELEPORTATION_CORE = register("TELEPORTATION_CORE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STAR_APPLE = register("STAR_APPLE", new Item(new FabricItemSettings().group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(PFoodComponents.STAR_APPLE)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item ENCHANTED_STAR_APPLE = register("ENCHANTED_STAR_APPLE", new EnchantedGoldenAppleItem(new FabricItemSettings().group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(PFoodComponents.ENCHANTED_STAR_APPLE)), (modelSupplier, self) -> modelSupplier.register(self, PItems.STAR_APPLE, Models.GENERATED)).finished();
    public static final Item COSMIC_SOUP = register("COSMIC_SOUP", new StewItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).rarity(Rarity.EPIC).food(PFoodComponents.COSMIC_SOUP)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();


    // Upgrades
    public static final Item MECHANICAL_BOOTS = register("MECHANICAL_BOOTS", new ArmorItem(SpecialArmorMaterials.MECHANICAL_BOOTS, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item ANGEL_RING = register("ANGEL_RING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item LIVING_SOUL_FRAGMENT = register("LIVING_SOUL_FRAGMENT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item SILENT_HEART = register("SILENT_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.RARE).food(PFoodComponents.SILENT_HEART)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item ANCHOR = register("ANCHOR", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item DREAM_CATCHER = register("DREAM_CATCHER", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item OCEANS_GRACE = register("OCEANS_GRACE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Ocean's Grace").finished();

    // Steel
    public static final Item STEEL_INGOT = register("STEEL_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STEEL_BINDING = register("STEEL_BINDING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Steel tier armor
    public static final Item STEEL_HELMET = register("STEEL_HELMET", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_CHESTPLATE = register("STEEL_CHESTPLATE", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_LEGGINGS = register("STEEL_LEGGINGS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_BOOTS = register("STEEL_BOOTS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();

    // Steel tier weapons
    public static final Item STEEL_BOW = register("STEEL_BOW", new TieredBowItem(BowMaterials.STEEL, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_SWORD = register("STEEL_SWORD", new SwordItem(PToolMaterials.STEEL, 4, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();

    // Steel tier tools
    public static final Item STEEL_AXE = register("STEEL_AXE", new AxeItem(PToolMaterials.STEEL, 6.0F, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_HOE = register("STEEL_HOE", new HoeItem(PToolMaterials.STEEL, -1, -1.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_PICKAXE = register("STEEL_PICKAXE", new PickaxeItem(PToolMaterials.STEEL, 2, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_SHOVEL = register("STEEL_SHOVEL", new ShovelItem(PToolMaterials.STEEL, 1F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();

    // Ultimate diamond
    public static final Item DIAMOND_HEART = register("DIAMOND_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Ultimate diamond tier armor
    public static final Item ULTIMATE_DIAMOND_HELMET = register("ULTIMATE_DIAMOND_HELMET", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_CHESTPLATE = register("ULTIMATE_DIAMOND_CHESTPLATE", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_LEGGINGS = register("ULTIMATE_DIAMOND_LEGGINGS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_BOOTS = register("ULTIMATE_DIAMOND_BOOTS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();

    // Ultimate diamond tier weapons
    public static final Item ULTIMATE_DIAMOND_BOW = register("ULTIMATE_DIAMOND_BOW", new TieredBowItem(BowMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_SWORD = register("ULTIMATE_DIAMOND_SWORD", new SwordItem(PToolMaterials.ULTIMATE_DIAMOND, 5, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();

    // Ultimate diamond tier tools
    public static final Item ULTIMATE_DIAMOND_AXE = register("ULTIMATE_DIAMOND_AXE", new AxeItem(PToolMaterials.ULTIMATE_DIAMOND, 8.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_HOE = register("ULTIMATE_DIAMOND_HOE", new HoeItem(PToolMaterials.ULTIMATE_DIAMOND, -2, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_PICKAXE = register("ULTIMATE_DIAMOND_PICKAXE", new PickaxeItem(PToolMaterials.ULTIMATE_DIAMOND, 4, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_SHOVEL = register("ULTIMATE_DIAMOND_SHOVEL", new ShovelItem(PToolMaterials.ULTIMATE_DIAMOND, 3F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();


    // Refined obsidian
    public static final Item REFINED_OBSIDIAN_INGOT = register("REFINED_OBSIDIAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item REFINED_OBSIDIAN_MODULE = register("REFINED_OBSIDIAN_MODULE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Refined obsidian tier armor
    public static final Item REFINED_OBSIDIAN_HELMET = register("REFINED_OBSIDIAN_HELMET", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_CHESTPLATE = register("REFINED_OBSIDIAN_CHESTPLATE", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_LEGGINGS = register("REFINED_OBSIDIAN_LEGGINGS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_BOOTS = register("REFINED_OBSIDIAN_BOOTS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();

    // Refined obsidian tier weapons
    public static final Item REFINED_OBSIDIAN_BOW = register("REFINED_OBSIDIAN_BOW", new TieredBowItem(BowMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_SWORD = register("REFINED_OBSIDIAN_SWORD", new SwordItem(PToolMaterials.REFINED_OBSIDIAN, 8, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();

    // Refined obsidian tier tools
    public static final Item REFINED_OBSIDIAN_AXE = register("REFINED_OBSIDIAN_AXE", new AxeItem(PToolMaterials.REFINED_OBSIDIAN, 12.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_HOE = register("REFINED_OBSIDIAN_HOE", new HoeItem(PToolMaterials.REFINED_OBSIDIAN, -2, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_PICKAXE = register("REFINED_OBSIDIAN_PICKAXE", new PickaxeItem(PToolMaterials.REFINED_OBSIDIAN, 8, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_SHOVEL = register("REFINED_OBSIDIAN_SHOVEL", new ShovelItem(PToolMaterials.REFINED_OBSIDIAN, 6F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();


    // Titan
    public static final Item EMBERITE = register("EMBERITE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item COMPRESSED_QUARTZ = register("COMPRESSED_QUARTZ", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item TITAN_INGOT = register("TITAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item TITAN_CORE = register("TITAN_CORE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Titan tier armor
    public static final Item TITAN_HELMET = register("TITAN_HELMET", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_CHESTPLATE = register("TITAN_CHESTPLATE", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_LEGGINGS = register("TITAN_LEGGINGS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_BOOTS = register("TITAN_BOOTS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();

    // Titan tier weapons
    public static final Item TITAN_BOW = register("TITAN_BOW", new TieredBowItem(BowMaterials.TITAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_SWORD = register("TITAN_SWORD", new SwordItem(PToolMaterials.TITAN, 17, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();

    // Titan tier tools
    public static final Item TITAN_AXE = register("TITAN_AXE", new AxeItem(PToolMaterials.TITAN, 22.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_HOE = register("TITAN_HOE", new HoeItem(PToolMaterials.TITAN, -3, 2.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_PICKAXE = register("TITAN_PICKAXE", new PickaxeItem(PToolMaterials.TITAN, 12, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_SHOVEL = register("TITAN_SHOVEL", new ShovelItem(PToolMaterials.TITAN, 10F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();

    // Primal netherite
    public static final Item NETHERITE_HEART = register("NETHERITE_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Primal netherite tier armor
    public static final Item PRIMAL_NETHERITE_HELMET = register("PRIMAL_NETHERITE_HELMET", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_CHESTPLATE = register("PRIMAL_NETHERITE_CHESTPLATE", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_LEGGINGS = register("PRIMAL_NETHERITE_LEGGINGS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_BOOTS = register("PRIMAL_NETHERITE_BOOTS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();

    // Primal netherite tier weapons
    public static final Item PRIMAL_NETHERITE_BOW = register("PRIMAL_NETHERITE_BOW", new TieredBowItem(BowMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_SWORD = register("PRIMAL_NETHERITE_SWORD", new SwordItem(PToolMaterials.PRIMAL_NETHERITE, 31, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();

    // Primal netherite tier tools
    public static final Item PRIMAL_NETHERITE_AXE = register("PRIMAL_NETHERITE_AXE", new AxeItem(PToolMaterials.PRIMAL_NETHERITE, 39F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_HOE = register("PRIMAL_NETHERITE_HOE", new HoeItem(PToolMaterials.PRIMAL_NETHERITE, 6, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_PICKAXE = register("PRIMAL_NETHERITE_PICKAXE", new PickaxeItem(PToolMaterials.PRIMAL_NETHERITE, 20, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_SHOVEL = register("PRIMAL_NETHERITE_SHOVEL", new ShovelItem(PToolMaterials.PRIMAL_NETHERITE, 15F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();


    // Verum
    public static final Item VERUM_INGOT = register("VERUM_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item RAW_VERUM = register("RAW_VERUM", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item VOID_SOUL_HELMET = register("VOID_SOUL_HELMET", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Helmet)").finished();
    public static final Item VOID_SOUL_CHESTPLATE = register("VOID_SOUL_CHESTPLATE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Chestplate)").finished();
    public static final Item VOID_SOUL_LEGGINGS = register("VOID_SOUL_LEGGINGS", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Leggings)").finished();
    public static final Item VOID_SOUL_BOOTS = register("VOID_SOUL_BOOTS", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Boots)").finished();
    public static final Item VOID_SOUL_TOOL = register("VOID_SOUL_TOOL", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Tool)").finished();

    // Verum tier armor
    public static final Item END_HELMET = register("END_HELMET", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_CHESTPLATE = register("END_CHESTPLATE", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_LEGGINGS = register("END_LEGGINGS", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_BOOTS = register("END_BOOTS", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();

    // Verum tier weapons
    public static final Item END_BOW = register("END_BOW", new TieredBowItem(BowMaterials.VERUM, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_SWORD = register("END_SWORD", new SwordItem(PToolMaterials.VERUM, 50, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();

    // Verum tier tools
    public static final Item END_AXE = register("END_AXE", new AxeItem(PToolMaterials.VERUM, 64.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_HOE = register("END_HOE", new HoeItem(PToolMaterials.VERUM, 12, 2.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_PICKAXE = register("END_PICKAXE", new PickaxeItem(PToolMaterials.VERUM, 37, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();
    public static final Item END_SHOVEL = register("END_SHOVEL", new ShovelItem(PToolMaterials.VERUM, 30F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.VERUM)).finished();

    // Doesn't work
    public static HoeItem createHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        HoeItem item = new HoeItem(material, (int) attackDamage, attackSpeed, settings);
        item.attackDamage = attackDamage;
        return item;
    }

    public static class ItemBuilder {
        protected final String id;
        protected final Item item;
        protected final BiConsumer<ItemModelGenerator, Item> modelSupplier;
        protected List<TagKey<Item>> tags = List.of();
        protected String name = null;

        public ItemBuilder(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier) {
            this.id = id;
            this.item = item;
            this.modelSupplier = modelSupplier;
        }

        public ItemBuilder tags(List<TagKey<Item>> tags) {
            this.tags = tags;
            return this;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Item finished() {
            return registerItem(id, name, item, modelSupplier, tags);
        }
    }

    public static ItemBuilder register(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier) {
        return new ItemBuilder(id, item, modelSupplier);
    }

    private static Item registerItem(String id, String name, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, List<TagKey<Item>> tags) {
        id = id.toLowerCase();
        tags = new ArrayList<>(tags);

        List<TagKey<Item>> finalTags = tags;
        Optional<PTierData> maybeTierData = Arrays.stream(PTierData.values())
                .filter(tierData -> finalTags.contains(tierData.tierAttribute))
                .findFirst();
        if (maybeTierData.isPresent()) {
            PTierData matchingTier = maybeTierData.get();
            int matchingMiningLevel = matchingTier.toolMaterial.getMiningLevel();

            // Iterate over PTierData to find entries with mining level <= matchingMiningLevel
            for (PTierData tier : PTierData.values()) {
                if (tier.toolMaterial.getMiningLevel() <= matchingMiningLevel) {
                    tags.add(tier.tierOrHigherAttribute);
                }
            }
        }

        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), item);
        tags = new ArrayList<>(tags == null ? List.of() : tags);
        if (item instanceof PickaxeItem) tags.add(ItemTags.CLUSTER_MAX_HARVESTABLES);

        if (name == null) name = StringUtils.toNormalCase(id);
        data.put(registeredItem, new ItemData(name, ms -> modelSupplier.accept(ms, registeredItem), tags));

        return registeredItem;
    }

    // Removed cause easier to tweak if all upgrades are in one place.
//    private static Item registerUpgrade(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, List<TagKey<Item>> tags, Function<Item, List<UEffect>> effects) {
//        Item registeredItem = registerItem(id, item, modelSupplier, tags);
//        Upgrades.register(registeredItem, effects);
//        return registeredItem;
//    }
//
//    private static Item registerUpgrade(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, Function<Item, List<UEffect>> effects) {
//        return registerUpgrade(id, item, modelSupplier, List.of(), effects);
//    }

    public static void init(){
        Prog.LOGGER.info("Registering Items for: " + Prog.MOD_ID);
    }
}
