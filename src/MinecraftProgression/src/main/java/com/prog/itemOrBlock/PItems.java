package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.itemOrBlock.custom.*;
import com.prog.itemOrBlock.tiers.*;
import com.prog.utils.LOGGER;
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
    public static final Item HEART_OF_GREED = register("HEART_OF_GREED", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STAR_APPLE = register("STAR_APPLE", new Item(new FabricItemSettings().group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(PFoodComponents.STAR_APPLE)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item ENCHANTED_STAR_APPLE = register("ENCHANTED_STAR_APPLE", new EnchantedGoldenAppleItem(new FabricItemSettings().group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(PFoodComponents.ENCHANTED_STAR_APPLE)), (modelSupplier, self) -> modelSupplier.register(self, PItems.STAR_APPLE, Models.GENERATED)).finished();
    public static final Item COSMIC_SOUP = register("COSMIC_SOUP", new StewItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1).rarity(Rarity.EPIC).food(PFoodComponents.COSMIC_SOUP)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item BURNED_CD = register("BURNED_CD", new Item(new FabricItemSettings().group(ItemGroup.MISC).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Burned CD").finished();
    public static final Item RUBY = register("RUBY", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STAR_FRAGMENT = register("STAR_FRAGMENT", new Item(new FabricItemSettings().group(ItemGroup.MISC).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STAR = register("STAR", new Item(new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.RARE).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Fishing
    public static final Item BLUE_ALGAE = register("BLUE_ALGAE", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.BLUE_ALGAE).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item BLACK_BASS = register("BLACK_BASS", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.BLACK_BASS).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item GOLDEN_LOBSTER = register("GOLDEN_LOBSTER", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.GOLDEN_LOBSTER).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item RAINBOWFISH = register("RAINBOWFISH", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.RAINBOWFISH).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item SUNFISH = register("SUNFISH", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.SUNFISH).fireproof()), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item FLYING_FISH = register("FLYING_FISH", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.FLYING_FISH)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item SPACE_EEL = register("SPACE_EEL", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.SPACE_EEL)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STELLAR_JELLY = register("STELLAR_JELLY", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.STELLAR_JELLY)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item VACUUM_FISH = register("VACUUM_FISH", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(PFoodComponents.VACUUM_FISH)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();

    // Upgrades
    public static final Item MECHANICAL_BOOTS = register("MECHANICAL_BOOTS", new ArmorItem(SpecialArmorMaterials.MECHANICAL_BOOTS, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item ANGEL_RING = register("ANGEL_RING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item LIVING_SOUL_FRAGMENT = register("LIVING_SOUL_FRAGMENT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item SILENT_HEART = register("SILENT_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.RARE).food(PFoodComponents.SILENT_HEART)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item ANCHOR = register("ANCHOR", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item DREAM_CATCHER = register("DREAM_CATCHER", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item OCEANS_GRACE = register("OCEANS_GRACE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Ocean's Grace").finished();

    // Amethyst
    public static final Item AMETHYST_HEART = register("AMETHYST_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();
    public static final Item AMETHYST_SHEARS = register("AMETHYST_SHEARS", new TieredShearsItem(ShearsMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();
    public static final Item FLINT_AND_AMETHYST = register("FLINT_AND_AMETHYST", new FlintAndSteelItem(new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).name("Flint and Amethyst").finished();

    // Trident
    public static final Item AMETHYST_TRIDENT = register("AMETHYST_TRIDENT", new TieredTridentItem(TridentMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();
    public static final Item APOCALYPTIC_TRIDENT = register("APOCALYPTIC_TRIDENT", new TieredTridentItem(TridentMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();
    public static final Item HELL_TRIDENT = register("HELL_TRIDENT", new TieredTridentItem(TridentMaterials.TITAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();
    public static final Item PRIMAL_TRIDENT = register("PRIMAL_TRIDENT", new TieredTridentItem(TridentMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();
    public static final Item SANGUINE_TRIDENT = register("SANGUINE_TRIDENT", new TieredTridentItem(TridentMaterials.EVERGLOOM, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();
    public static final Item STELLAR_TRIDENT = register("STELLAR_TRIDENT", new TieredTridentItem(TridentMaterials.END, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE)).finished();

    // Steel
    public static final Item STEEL_INGOT = register("STEEL_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STEEL_BINDING = register("STEEL_BINDING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // Steel tier armor
    public static final Item STEEL_HELMET = register("STEEL_HELMET", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_CHESTPLATE = register("STEEL_CHESTPLATE", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_LEGGINGS = register("STEEL_LEGGINGS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_BOOTS = register("STEEL_BOOTS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();

    // Steel tier weapons
    public static final Item STEEL_BOW = register("STEEL_BOW", new TieredBowItem(BowMaterials.STEEL, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_CROSSBOW = register("STEEL_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.STEEL, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_SWORD = register("STEEL_SWORD", new SwordItem(PToolMaterials.STEEL, PToolMaterials.STEEL.sword.damage, PToolMaterials.STEEL.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_SHIELD = register("STEEL_SHIELD", new TieredShieldItem(ShieldMaterials.STEEL, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();

    // Steel tier tools
    public static final Item STEEL_AXE = register("STEEL_AXE", new AxeItem(PToolMaterials.STEEL, PToolMaterials.STEEL.axe.damage, PToolMaterials.STEEL.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_HOE = register("STEEL_HOE", new HoeItem(PToolMaterials.STEEL, PToolMaterials.STEEL.hoe.damage, PToolMaterials.STEEL.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_PICKAXE = register("STEEL_PICKAXE", new PickaxeItem(PToolMaterials.STEEL, PToolMaterials.STEEL.pickaxe.damage, PToolMaterials.STEEL.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_SHOVEL = register("STEEL_SHOVEL", new ShovelItem(PToolMaterials.STEEL, PToolMaterials.STEEL.shovel.damage, PToolMaterials.STEEL.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();
    public static final Item STEEL_FISHING_ROD = register("STEEL_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.STEEL, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.STEEL)).finished();

    // Ultimate diamond
    public static final Item DIAMOND_HEART = register("DIAMOND_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // Ultimate diamond tier armor
    public static final Item ULTIMATE_DIAMOND_HELMET = register("ULTIMATE_DIAMOND_HELMET", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_CHESTPLATE = register("ULTIMATE_DIAMOND_CHESTPLATE", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_LEGGINGS = register("ULTIMATE_DIAMOND_LEGGINGS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_BOOTS = register("ULTIMATE_DIAMOND_BOOTS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();

    // Ultimate diamond tier weapons
    public static final Item ULTIMATE_DIAMOND_BOW = register("ULTIMATE_DIAMOND_BOW", new TieredBowItem(BowMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_CROSSBOW = register("ULTIMATE_DIAMOND_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_SWORD = register("ULTIMATE_DIAMOND_SWORD", new SwordItem(PToolMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND.sword.damage, PToolMaterials.ULTIMATE_DIAMOND.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_SHIELD = register("ULTIMATE_DIAMOND_SHIELD", new TieredShieldItem(ShieldMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();

    // Ultimate diamond tier tools
    public static final Item ULTIMATE_DIAMOND_AXE = register("ULTIMATE_DIAMOND_AXE", new AxeItem(PToolMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND.axe.damage, PToolMaterials.ULTIMATE_DIAMOND.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_HOE = register("ULTIMATE_DIAMOND_HOE", new HoeItem(PToolMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND.hoe.damage, PToolMaterials.ULTIMATE_DIAMOND.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_PICKAXE = register("ULTIMATE_DIAMOND_PICKAXE", new PickaxeItem(PToolMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND.pickaxe.damage, PToolMaterials.ULTIMATE_DIAMOND.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_SHOVEL = register("ULTIMATE_DIAMOND_SHOVEL", new ShovelItem(PToolMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND.shovel.damage, PToolMaterials.ULTIMATE_DIAMOND.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();
    public static final Item ULTIMATE_DIAMOND_FISHING_ROD = register("ULTIMATE_DIAMOND_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.ULTIMATE_DIAMOND, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.ULTIMATE_DIAMOND)).finished();


    // Refined obsidian
    public static final Item REFINED_OBSIDIAN_INGOT = register("REFINED_OBSIDIAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item REFINED_OBSIDIAN_MODULE = register("REFINED_OBSIDIAN_MODULE", new Item(new FabricItemSettings().group(ItemGroup.REDSTONE)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();
    public static final Item APOCALYPTIC_SCEPTER = register("APOCALYPTIC_SCEPTER", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // Refined obsidian tier armor
    public static final Item REFINED_OBSIDIAN_HELMET = register("REFINED_OBSIDIAN_HELMET", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_CHESTPLATE = register("REFINED_OBSIDIAN_CHESTPLATE", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_LEGGINGS = register("REFINED_OBSIDIAN_LEGGINGS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_BOOTS = register("REFINED_OBSIDIAN_BOOTS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();

    // Refined obsidian tier weapons
    public static final Item REFINED_OBSIDIAN_BOW = register("REFINED_OBSIDIAN_BOW", new TieredBowItem(BowMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_CROSSBOW = register("REFINED_OBSIDIAN_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_SWORD = register("REFINED_OBSIDIAN_SWORD", new SwordItem(PToolMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN.sword.damage, PToolMaterials.REFINED_OBSIDIAN.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_SHIELD = register("REFINED_OBSIDIAN_SHIELD", new TieredShieldItem(ShieldMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();

    // Refined obsidian tier tools
    public static final Item REFINED_OBSIDIAN_AXE = register("REFINED_OBSIDIAN_AXE", new AxeItem(PToolMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN.axe.damage, PToolMaterials.REFINED_OBSIDIAN.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_HOE = register("REFINED_OBSIDIAN_HOE", new HoeItem(PToolMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN.hoe.damage, PToolMaterials.REFINED_OBSIDIAN.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_PICKAXE = register("REFINED_OBSIDIAN_PICKAXE", new PickaxeItem(PToolMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN.pickaxe.damage, PToolMaterials.REFINED_OBSIDIAN.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_SHOVEL = register("REFINED_OBSIDIAN_SHOVEL", new ShovelItem(PToolMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN.shovel.damage, PToolMaterials.REFINED_OBSIDIAN.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();
    public static final Item REFINED_OBSIDIAN_FISHING_ROD = register("REFINED_OBSIDIAN_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.REFINED_OBSIDIAN, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.REFINED_OBSIDIAN)).finished();


    // Titan
    public static final Item EMBERITE = register("EMBERITE", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item FLAME_ORB = register("FLAME_ORB", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();
    public static final Item COMPRESSED_QUARTZ = register("COMPRESSED_QUARTZ", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item TITAN_INGOT = register("TITAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item TITAN_CORE = register("TITAN_CORE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // Titan tier armor
    public static final Item TITAN_HELMET = register("TITAN_HELMET", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_CHESTPLATE = register("TITAN_CHESTPLATE", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_LEGGINGS = register("TITAN_LEGGINGS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_BOOTS = register("TITAN_BOOTS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();

    // Titan tier weapons
    public static final Item TITAN_BOW = register("TITAN_BOW", new TieredBowItem(BowMaterials.TITAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_CROSSBOW = register("TITAN_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.TITAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_SWORD = register("TITAN_SWORD", new SwordItem(PToolMaterials.TITAN, PToolMaterials.TITAN.sword.damage, PToolMaterials.TITAN.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_SHIELD = register("TITAN_SHIELD", new TieredShieldItem(ShieldMaterials.TITAN, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();

    // Titan tier tools
    public static final Item TITAN_AXE = register("TITAN_AXE", new AxeItem(PToolMaterials.TITAN, PToolMaterials.TITAN.axe.damage, PToolMaterials.TITAN.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_HOE = register("TITAN_HOE", new HoeItem(PToolMaterials.TITAN, PToolMaterials.TITAN.hoe.damage, PToolMaterials.TITAN.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_PICKAXE = register("TITAN_PICKAXE", new PickaxeItem(PToolMaterials.TITAN, PToolMaterials.TITAN.pickaxe.damage, PToolMaterials.TITAN.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_SHOVEL = register("TITAN_SHOVEL", new ShovelItem(PToolMaterials.TITAN, PToolMaterials.TITAN.shovel.damage, PToolMaterials.TITAN.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();
    public static final Item TITAN_FISHING_ROD = register("TITAN_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.TITAN, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.TITAN)).finished();


    // Primal netherite
    public static final Item NETHERITE_HEART = register("NETHERITE_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // Primal netherite tier armor
    public static final Item PRIMAL_NETHERITE_HELMET = register("PRIMAL_NETHERITE_HELMET", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_CHESTPLATE = register("PRIMAL_NETHERITE_CHESTPLATE", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_LEGGINGS = register("PRIMAL_NETHERITE_LEGGINGS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_BOOTS = register("PRIMAL_NETHERITE_BOOTS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();

    // Primal netherite tier weapons
    public static final Item PRIMAL_NETHERITE_BOW = register("PRIMAL_NETHERITE_BOW", new TieredBowItem(BowMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_CROSSBOW = register("PRIMAL_NETHERITE_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_SWORD = register("PRIMAL_NETHERITE_SWORD", new SwordItem(PToolMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE.sword.damage, PToolMaterials.PRIMAL_NETHERITE.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_SHIELD = register("PRIMAL_NETHERITE_SHIELD", new TieredShieldItem(ShieldMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();

    // Primal netherite tier tools
    public static final Item PRIMAL_NETHERITE_AXE = register("PRIMAL_NETHERITE_AXE", new AxeItem(PToolMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE.axe.damage, PToolMaterials.PRIMAL_NETHERITE.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_HOE = register("PRIMAL_NETHERITE_HOE", new HoeItem(PToolMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE.hoe.damage, PToolMaterials.PRIMAL_NETHERITE.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_PICKAXE = register("PRIMAL_NETHERITE_PICKAXE", new PickaxeItem(PToolMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE.pickaxe.damage, PToolMaterials.PRIMAL_NETHERITE.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_SHOVEL = register("PRIMAL_NETHERITE_SHOVEL", new ShovelItem(PToolMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE.shovel.damage, PToolMaterials.PRIMAL_NETHERITE.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();
    public static final Item PRIMAL_NETHERITE_FISHING_ROD = register("PRIMAL_NETHERITE_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.PRIMAL_NETHERITE, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.PRIMAL_NETHERITE)).finished();


    // Evergloom
    public static final Item PURE_EVERGLOOM_INGOT = register("PURE_EVERGLOOM_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item KNOWLEDGE_POWDER = register("KNOWLEDGE_POWDER", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();
    public static final Item PURE_EVERBLOOD_INGOT = register("PURE_EVERBLOOD_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item DEMON_HEART = register("DEMON_HEART", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // Evergloom tier armor
    public static final Item EVERGLOOM_HELMET = register("EVERGLOOM_HELMET", new ArmorItem(PArmorMaterials.EVERGLOOM, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_CHESTPLATE = register("EVERGLOOM_CHESTPLATE", new ArmorItem(PArmorMaterials.EVERGLOOM, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_LEGGINGS = register("EVERGLOOM_LEGGINGS", new ArmorItem(PArmorMaterials.EVERGLOOM, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_BOOTS = register("EVERGLOOM_BOOTS", new ArmorItem(PArmorMaterials.EVERGLOOM, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();

    // Evergloom tier weapons
    public static final Item EVERGLOOM_BOW = register("EVERGLOOM_BOW", new TieredBowItem(BowMaterials.EVERGLOOM, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_CROSSBOW = register("EVERGLOOM_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.EVERGLOOM, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_SWORD = register("EVERGLOOM_SWORD", new SwordItem(PToolMaterials.EVERGLOOM, PToolMaterials.EVERGLOOM.sword.damage, PToolMaterials.EVERGLOOM.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_SHIELD = register("EVERGLOOM_SHIELD", new TieredShieldItem(ShieldMaterials.EVERGLOOM, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();

    // Evergloom tier tools
    public static final Item EVERGLOOM_AXE = register("EVERGLOOM_AXE", new AxeItem(PToolMaterials.EVERGLOOM, PToolMaterials.EVERGLOOM.axe.damage, PToolMaterials.EVERGLOOM.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_HOE = register("EVERGLOOM_HOE", new HoeItem(PToolMaterials.EVERGLOOM, PToolMaterials.EVERGLOOM.hoe.damage, PToolMaterials.EVERGLOOM.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_PICKAXE = register("EVERGLOOM_PICKAXE", new PickaxeItem(PToolMaterials.EVERGLOOM, PToolMaterials.EVERGLOOM.pickaxe.damage, PToolMaterials.EVERGLOOM.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_SHOVEL = register("EVERGLOOM_SHOVEL", new ShovelItem(PToolMaterials.EVERGLOOM, PToolMaterials.EVERGLOOM.shovel.damage, PToolMaterials.EVERGLOOM.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();
    public static final Item EVERGLOOM_FISHING_ROD = register("EVERGLOOM_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.EVERGLOOM, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.EVERGLOOM)).finished();


    // End
    public static final Item VERUM_INGOT = register("VERUM_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item RAW_VERUM = register("RAW_VERUM", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item STELLAR_SHARD = register("STELLAR_SHARD", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).finished();
    public static final Item VOID_SOUL_HELMET = register("VOID_SOUL_HELMET", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Helmet)").tierCore().finished();
    public static final Item VOID_SOUL_CHESTPLATE = register("VOID_SOUL_CHESTPLATE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Chestplate)").tierCore().finished();
    public static final Item VOID_SOUL_LEGGINGS = register("VOID_SOUL_LEGGINGS", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Leggings)").tierCore().finished();
    public static final Item VOID_SOUL_BOOTS = register("VOID_SOUL_BOOTS", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Boots)").tierCore().finished();
    public static final Item VOID_SOUL_TOOL = register("VOID_SOUL_TOOL", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).name("Void Soul (Tool)").tierCore().finished();
    public static final Item STELLAR_SOUL = register("STELLAR_SOUL", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tierCore().finished();

    // End tier armor
    public static final Item END_HELMET = register("END_HELMET", new ArmorItem(PArmorMaterials.END, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_CHESTPLATE = register("END_CHESTPLATE", new ArmorItem(PArmorMaterials.END, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_LEGGINGS = register("END_LEGGINGS", new ArmorItem(PArmorMaterials.END, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_BOOTS = register("END_BOOTS", new ArmorItem(PArmorMaterials.END, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();

    // End tier weapons
    public static final Item END_BOW = register("END_BOW", new TieredBowItem(BowMaterials.END, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_CROSSBOW = register("END_CROSSBOW", new TieredCrossbowItem(CrossbowMaterials.END, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_SWORD = register("END_SWORD", new SwordItem(PToolMaterials.END, PToolMaterials.END.sword.damage, PToolMaterials.END.sword.attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_SHIELD = register("END_SHIELD", new TieredShieldItem(ShieldMaterials.END, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> {
    }).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();

    // End tier tools
    public static final Item END_AXE = register("END_AXE", new AxeItem(PToolMaterials.END, PToolMaterials.END.axe.damage, PToolMaterials.END.axe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_HOE = register("END_HOE", new HoeItem(PToolMaterials.END, PToolMaterials.END.hoe.damage, PToolMaterials.END.hoe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_PICKAXE = register("END_PICKAXE", new PickaxeItem(PToolMaterials.END, PToolMaterials.END.pickaxe.damage, PToolMaterials.END.pickaxe.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_SHOVEL = register("END_SHOVEL", new ShovelItem(PToolMaterials.END, PToolMaterials.END.shovel.damage, PToolMaterials.END.shovel.attackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_FISHING_ROD = register("END_FISHING_ROD", new TieredFishingRodItem(FishingRodMaterials.END, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> {}).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    public static final Item END_SHEARS = register("END_SHEARS", new TieredShearsItem(ShearsMaterials.END, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED)).tags(List.of(PItemTags.UPGRADABLE, PItemTags.END)).finished();
    

    public static class ItemBuilder {
        protected final String id;
        protected final Item item;
        protected final BiConsumer<ItemModelGenerator, Item> modelSupplier;
        protected List<TagKey<Item>> tags = new ArrayList<>();
        protected String name = null;

        public ItemBuilder(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier) {
            this.id = id;
            this.item = item;
            this.modelSupplier = modelSupplier;
        }

        public ItemBuilder tags(List<TagKey<Item>> tags) {
            this.tags.addAll(tags);
            return this;
        }

        public ItemBuilder tierCore() {
            this.tags.add(PItemTags.TIER_CORE);
            return this;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Item finished() {
            return registerItem(id, name, item, modelSupplier, tags.stream().toList());
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
        LOGGER.info("Registering Items for: " + Prog.MOD_ID);
    }
}
