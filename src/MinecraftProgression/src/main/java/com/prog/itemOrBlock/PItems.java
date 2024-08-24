package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.itemOrBlock.custom.TieredBowItem;
import com.prog.itemOrBlock.tiers.*;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
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
import java.util.function.Function;

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
    public static final Item MACHINE_CIRCUIT = registerItem("MACHINE_CIRCUIT", new Item(new FabricItemSettings().group(ItemGroup.REDSTONE)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item TELEPORTATION_CORE = registerItem("TELEPORTATION_CORE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STAR_APPLE = registerItem("STAR_APPLE", new Item(new FabricItemSettings().group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(PFoodComponents.STAR_APPLE)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item ENCHANTED_STAR_APPLE = registerItem("ENCHANTED_STAR_APPLE", new EnchantedGoldenAppleItem(new FabricItemSettings().group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(PFoodComponents.ENCHANTED_STAR_APPLE)), (modelSupplier, self) -> modelSupplier.register(self, PItems.STAR_APPLE, Models.GENERATED));

    // Steel
    public static final Item STEEL_INGOT = registerItem("STEEL_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_BINDING = registerItem("STEEL_BINDING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Steel tier armor
    public static final Item STEEL_HELMET = registerItem("STEEL_HELMET", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_CHESTPLATE = registerItem("STEEL_CHESTPLATE", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_LEGGINGS = registerItem("STEEL_LEGGINGS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_BOOTS = registerItem("STEEL_BOOTS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));

    // Steel tier weapons
    public static final Item STEEL_BOW = registerItem("STEEL_BOW", new TieredBowItem(BowMaterials.STEEL, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }, List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_SWORD = registerItem("STEEL_SWORD", new SwordItem(PToolMaterials.STEEL, 4, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));

    // Steel tier tools
    public static final Item STEEL_AXE = registerItem("STEEL_AXE", new AxeItem(PToolMaterials.STEEL, 6.0F, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_HOE = registerItem("STEEL_HOE", new HoeItem(PToolMaterials.STEEL, -1, -1.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_PICKAXE = registerItem("STEEL_PICKAXE", new PickaxeItem(PToolMaterials.STEEL, 2, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));
    public static final Item STEEL_SHOVEL = registerItem("STEEL_SHOVEL", new ShovelItem(PToolMaterials.STEEL, 1F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.STEEL));

    // Ultimate diamond
    public static final Item DIAMOND_HEART = registerItem("DIAMOND_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Ultimate diamond tier armor
    public static final Item ULTIMATE_DIAMOND_HELMET = registerItem("ULTIMATE_DIAMOND_HELMET", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_CHESTPLATE = registerItem("ULTIMATE_DIAMOND_CHESTPLATE", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_LEGGINGS = registerItem("ULTIMATE_DIAMOND_LEGGINGS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_BOOTS = registerItem("ULTIMATE_DIAMOND_BOOTS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));

    // Ultimate diamond tier weapons
    public static final Item ULTIMATE_DIAMOND_BOW = registerItem("ULTIMATE_DIAMOND_BOW", new TieredBowItem(BowMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }, List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_SWORD = registerItem("ULTIMATE_DIAMOND_SWORD", new SwordItem(PToolMaterials.ULTIMATE_DIAMOND, 5, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));

    // Ultimate diamond tier tools
    public static final Item ULTIMATE_DIAMOND_AXE = registerItem("ULTIMATE_DIAMOND_AXE", new AxeItem(PToolMaterials.ULTIMATE_DIAMOND, 8.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_HOE = registerItem("ULTIMATE_DIAMOND_HOE", new HoeItem(PToolMaterials.ULTIMATE_DIAMOND, -2, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_PICKAXE = registerItem("ULTIMATE_DIAMOND_PICKAXE", new PickaxeItem(PToolMaterials.ULTIMATE_DIAMOND, 4, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));
    public static final Item ULTIMATE_DIAMOND_SHOVEL = registerItem("ULTIMATE_DIAMOND_SHOVEL", new ShovelItem(PToolMaterials.ULTIMATE_DIAMOND, 3F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND));


    // Refined obsidian
    public static final Item REFINED_OBSIDIAN_INGOT = registerItem("REFINED_OBSIDIAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item REFINED_OBSIDIAN_MODULE = registerItem("REFINED_OBSIDIAN_MODULE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Refined obsidian tier armor
    public static final Item REFINED_OBSIDIAN_HELMET = registerItem("REFINED_OBSIDIAN_HELMET", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_CHESTPLATE = registerItem("REFINED_OBSIDIAN_CHESTPLATE", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_LEGGINGS = registerItem("REFINED_OBSIDIAN_LEGGINGS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_BOOTS = registerItem("REFINED_OBSIDIAN_BOOTS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));

    // Refined obsidian tier weapons
    public static final Item REFINED_OBSIDIAN_BOW = registerItem("REFINED_OBSIDIAN_BOW", new TieredBowItem(BowMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }, List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_SWORD = registerItem("REFINED_OBSIDIAN_SWORD", new SwordItem(PToolMaterials.REFINED_OBSIDIAN, 8, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));

    // Refined obsidian tier tools
    public static final Item REFINED_OBSIDIAN_AXE = registerItem("REFINED_OBSIDIAN_AXE", new AxeItem(PToolMaterials.REFINED_OBSIDIAN, 12.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_HOE = registerItem("REFINED_OBSIDIAN_HOE", new HoeItem(PToolMaterials.REFINED_OBSIDIAN, -3, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_PICKAXE = registerItem("REFINED_OBSIDIAN_PICKAXE", new PickaxeItem(PToolMaterials.REFINED_OBSIDIAN, 8, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));
    public static final Item REFINED_OBSIDIAN_SHOVEL = registerItem("REFINED_OBSIDIAN_SHOVEL", new ShovelItem(PToolMaterials.REFINED_OBSIDIAN, 6F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN));


    // Titan
    public static final Item EMBERITE = registerItem("EMBERITE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item COMPRESSED_QUARTZ = registerItem("COMPRESSED_QUARTZ", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item TITAN_INGOT = registerItem("TITAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item TITAN_CORE = registerItem("TITAN_CORE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Titan tier armor
    public static final Item TITAN_HELMET = registerItem("TITAN_HELMET", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_CHESTPLATE = registerItem("TITAN_CHESTPLATE", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_LEGGINGS = registerItem("TITAN_LEGGINGS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_BOOTS = registerItem("TITAN_BOOTS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));

    // Titan tier weapons
    public static final Item TITAN_BOW = registerItem("TITAN_BOW", new TieredBowItem(BowMaterials.TITAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }, List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_SWORD = registerItem("TITAN_SWORD", new SwordItem(PToolMaterials.TITAN, 17, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));

    // Titan tier tools
    public static final Item TITAN_AXE = registerItem("TITAN_AXE", new AxeItem(PToolMaterials.TITAN, 22.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_HOE = registerItem("TITAN_HOE", new HoeItem(PToolMaterials.TITAN, -2, 2.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_PICKAXE = registerItem("TITAN_PICKAXE", new PickaxeItem(PToolMaterials.TITAN, 12, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item TITAN_SHOVEL = registerItem("TITAN_SHOVEL", new ShovelItem(PToolMaterials.TITAN, 10F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));


    // Primal netherite tier armor
    public static final Item PRIMAL_NETHERITE_HELMET = registerItem("PRIMAL_NETHERITE_HELMET", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_CHESTPLATE = registerItem("PRIMAL_NETHERITE_CHESTPLATE", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_LEGGINGS = registerItem("PRIMAL_NETHERITE_LEGGINGS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_BOOTS = registerItem("PRIMAL_NETHERITE_BOOTS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));

    // Primal netherite tier weapons
    public static final Item PRIMAL_NETHERITE_BOW = registerItem("PRIMAL_NETHERITE_BOW", new TieredBowItem(BowMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}, List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_SWORD = registerItem("PRIMAL_NETHERITE_SWORD", new SwordItem(PToolMaterials.PRIMAL_NETHERITE, 31, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));

    // Primal netherite tier tools
    public static final Item PRIMAL_NETHERITE_AXE = registerItem("PRIMAL_NETHERITE_AXE", new AxeItem(PToolMaterials.PRIMAL_NETHERITE, 39F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_HOE = registerItem("PRIMAL_NETHERITE_HOE", new HoeItem(PToolMaterials.PRIMAL_NETHERITE, 5, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_PICKAXE = registerItem("PRIMAL_NETHERITE_PICKAXE", new PickaxeItem(PToolMaterials.PRIMAL_NETHERITE, 20, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));
    public static final Item PRIMAL_NETHERITE_SHOVEL = registerItem("PRIMAL_NETHERITE_SHOVEL", new ShovelItem(PToolMaterials.PRIMAL_NETHERITE, 15F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE));


    // VERUM
    public static final Item VERUM_INGOT = registerItem("VERUM_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item RAW_VERUM = registerItem("RAW_VERUM", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Verum tier armor
    public static final Item VERUM_HELMET = registerItem("VERUM_HELMET", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));
    public static final Item VERUM_CHESTPLATE = registerItem("VERUM_CHESTPLATE", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.TITAN));
    public static final Item VERUM_LEGGINGS = registerItem("VERUM_LEGGINGS", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));
    public static final Item VERUM_BOOTS = registerItem("VERUM_BOOTS", new ArmorItem(PArmorMaterials.VERUM, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));

    // Verum tier weapons
    public static final Item VERUM_BOW = registerItem("VERUM_BOW", new TieredBowItem(BowMaterials.VERUM, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }, List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));
    public static final Item VERUM_SWORD = registerItem("VERUM_SWORD", new SwordItem(PToolMaterials.VERUM, 50, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));

    // Verum tier tools
    public static final Item VERUM_AXE = registerItem("VERUM_AXE", new AxeItem(PToolMaterials.VERUM, 64.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));
    public static final Item VERUM_HOE = registerItem("VERUM_HOE", new HoeItem(PToolMaterials.VERUM, 12, 2.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));
    public static final Item VERUM_PICKAXE = registerItem("VERUM_PICKAXE", new PickaxeItem(PToolMaterials.VERUM, 37, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));
    public static final Item VERUM_SHOVEL = registerItem("VERUM_SHOVEL", new ShovelItem(PToolMaterials.VERUM, 30F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE, PItemTags.VERUM));

    // Upgrades
    public static final Item MECHANICAL_BOOTS = registerItem("MECHANICAL_BOOTS", new ArmorItem(SpecialArmorMaterials.MECHANICAL_BOOTS, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item ANGEL_RING = registerItem("ANGEL_RING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Doesn't work
    public static HoeItem createHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        HoeItem item = new HoeItem(material, (int) attackDamage, attackSpeed, settings);
        item.attackDamage = attackDamage;
        return item;
    }

    private static Item registerItem(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, List<TagKey<Item>> tags) {
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

        String name = StringUtils.toNormalCase(id);
        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), item);
        tags = new ArrayList<>(tags == null ? List.of() : tags);
        if (item instanceof PickaxeItem) tags.add(ItemTags.CLUSTER_MAX_HARVESTABLES);
        data.put(registeredItem, new ItemData(name, ms -> modelSupplier.accept(ms, registeredItem), tags));
        return registeredItem;
    }

    private static Item registerItem(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier) {
        return registerItem(id, item, modelSupplier, List.of());
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
