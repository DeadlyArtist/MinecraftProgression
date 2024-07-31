package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.itemOrBlock.custom.UpgradeableArmorItem;
import com.prog.itemOrBlock.tiers.PArmorMaterials;
import com.prog.itemOrBlock.tiers.PToolMaterials;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    // Steel
    public static final Item STEEL_INGOT = registerItem("STEEL_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_BINDING = registerItem("STEEL_BINDING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Steel tier armor
    public static final Item STEEL_BOOTS = registerItem("STEEL_BOOTS", new UpgradeableArmorItem(PArmorMaterials.STEEL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item STEEL_CHESTPLATE = registerItem("STEEL_CHESTPLATE", new UpgradeableArmorItem(PArmorMaterials.STEEL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item STEEL_HELMET = registerItem("STEEL_HELMET", new UpgradeableArmorItem(PArmorMaterials.STEEL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item STEEL_LEGGINGS = registerItem("STEEL_LEGGINGS", new UpgradeableArmorItem(PArmorMaterials.STEEL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Steel tier tools
    public static final Item STEEL_AXE = registerItem("STEEL_AXE", new AxeItem(PToolMaterials.STEEL, 5.0F, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item STEEL_HOE = registerItem("STEEL_HOE", new HoeItem(PToolMaterials.STEEL, -2, -1.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item STEEL_PICKAXE = registerItem("STEEL_PICKAXE", new PickaxeItem(PToolMaterials.STEEL, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item STEEL_SHOVEL = registerItem("STEEL_SHOVEL", new ShovelItem(PToolMaterials.STEEL, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Steel tier weapons
    public static final Item STEEL_SWORD = registerItem("STEEL_SWORD", new SwordItem(PToolMaterials.STEEL, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));


    // Ultimate diamond
    public static final Item REFINED_OBSIDIAN_INGOT = registerItem("REFINED_OBSIDIAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Ultimate diamond tier armor
    public static final Item ULTIMATE_DIAMOND_BOOTS = registerItem("ULTIMATE_DIAMOND_BOOTS", new UpgradeableArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item ULTIMATE_DIAMOND_CHESTPLATE = registerItem("ULTIMATE_DIAMOND_CHESTPLATE", new UpgradeableArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item ULTIMATE_DIAMOND_HELMET = registerItem("ULTIMATE_DIAMOND_HELMET", new UpgradeableArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item ULTIMATE_DIAMOND_LEGGINGS = registerItem("ULTIMATE_DIAMOND_LEGGINGS", new UpgradeableArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Ultimate diamond tier tools
    public static final Item ULTIMATE_DIAMOND_AXE = registerItem("ULTIMATE_DIAMOND_AXE", new AxeItem(PToolMaterials.ULTIMATE_DIAMOND, 5.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item ULTIMATE_DIAMOND_HOE = registerItem("ULTIMATE_DIAMOND_HOE", createHoeItem(PToolMaterials.ULTIMATE_DIAMOND, -3F, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item ULTIMATE_DIAMOND_PICKAXE = registerItem("ULTIMATE_DIAMOND_PICKAXE", new PickaxeItem(PToolMaterials.ULTIMATE_DIAMOND, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item ULTIMATE_DIAMOND_SHOVEL = registerItem("ULTIMATE_DIAMOND_SHOVEL", new ShovelItem(PToolMaterials.ULTIMATE_DIAMOND, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Ultimate diamond tier weapons
    public static final Item ULTIMATE_DIAMOND_SWORD = registerItem("ULTIMATE_DIAMOND_SWORD", new SwordItem(PToolMaterials.ULTIMATE_DIAMOND, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));


    // Refined obsidian
    // Refined obsidian tier armor
    public static final Item REFINED_OBSIDIAN_BOOTS = registerItem("REFINED_OBSIDIAN_BOOTS", new UpgradeableArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item REFINED_OBSIDIAN_CHESTPLATE = registerItem("REFINED_OBSIDIAN_CHESTPLATE", new UpgradeableArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item REFINED_OBSIDIAN_HELMET = registerItem("REFINED_OBSIDIAN_HELMET", new UpgradeableArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item REFINED_OBSIDIAN_LEGGINGS = registerItem("REFINED_OBSIDIAN_LEGGINGS", new UpgradeableArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Refined obsidian tier tools
    public static final Item REFINED_OBSIDIAN_AXE = registerItem("REFINED_OBSIDIAN_AXE", new AxeItem(PToolMaterials.REFINED_OBSIDIAN, 5.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item REFINED_OBSIDIAN_HOE = registerItem("REFINED_OBSIDIAN_HOE", createHoeItem(PToolMaterials.REFINED_OBSIDIAN, -3.5F, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item REFINED_OBSIDIAN_PICKAXE = registerItem("REFINED_OBSIDIAN_PICKAXE", new PickaxeItem(PToolMaterials.REFINED_OBSIDIAN, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item REFINED_OBSIDIAN_SHOVEL = registerItem("REFINED_OBSIDIAN_SHOVEL", new ShovelItem(PToolMaterials.REFINED_OBSIDIAN, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Refined obsidian tier weapons
    public static final Item REFINED_OBSIDIAN_SWORD = registerItem("REFINED_OBSIDIAN_SWORD", new SwordItem(PToolMaterials.REFINED_OBSIDIAN, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));


//    // Titan
//    public static final Item TITAN_INGOT = registerItem("TITAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//
//    // Titan tier armor
//    public static final Item TITAN_BOOTS = registerItem("TITAN_BOOTS", new ArmorItem(PArmorMaterial.TITAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//    public static final Item TITAN_CHESTPLATE = registerItem("TITAN_CHESTPLATE", new ArmorItem(PArmorMaterial.TITAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//    public static final Item TITAN_HELMET = registerItem("TITAN_HELMET", new ArmorItem(PArmorMaterial.TITAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//    public static final Item TITAN_LEGGINGS = registerItem("TITAN_LEGGINGS", new ArmorItem(PArmorMaterial.TITAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//
//    // Titan tier tools
//    public static final Item TITAN_AXE = registerItem("TITAN_AXE", new AxeItem(PToolMaterial.TITAN, 6.0F, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//    public static final Item TITAN_HOE = registerItem("TITAN_HOE", new HoeItem(PToolMaterial.TITAN, -2, -1.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//    public static final Item TITAN_PICKAXE = registerItem("TITAN_PICKAXE", new PickaxeItem(PToolMaterial.TITAN, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//    public static final Item TITAN_SHOVEL = registerItem("TITAN_SHOVEL", new ShovelItem(PToolMaterial.TITAN, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
//
//    // Titan tier weapons
//    public static final Item TITAN_SWORD = registerItem("TITAN_SWORD", new SwordItem(PToolMaterial.TITAN, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));


    // Primal netherite tier armor
    public static final Item PRIMAL_NETHERITE_BOOTS = registerItem("PRIMAL_NETHERITE_BOOTS", new UpgradeableArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item PRIMAL_NETHERITE_CHESTPLATE = registerItem("PRIMAL_NETHERITE_CHESTPLATE", new UpgradeableArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item PRIMAL_NETHERITE_HELMET = registerItem("PRIMAL_NETHERITE_HELMET", new UpgradeableArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item PRIMAL_NETHERITE_LEGGINGS = registerItem("PRIMAL_NETHERITE_LEGGINGS", new UpgradeableArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Primal netherite tier tools
    public static final Item PRIMAL_NETHERITE_AXE = registerItem("PRIMAL_NETHERITE_AXE", new AxeItem(PToolMaterials.PRIMAL_NETHERITE, 5.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item PRIMAL_NETHERITE_HOE = registerItem("PRIMAL_NETHERITE_HOE", new HoeItem(PToolMaterials.PRIMAL_NETHERITE, -5, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item PRIMAL_NETHERITE_PICKAXE = registerItem("PRIMAL_NETHERITE_PICKAXE", new PickaxeItem(PToolMaterials.PRIMAL_NETHERITE, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));
    public static final Item PRIMAL_NETHERITE_SHOVEL = registerItem("PRIMAL_NETHERITE_SHOVEL", new ShovelItem(PToolMaterials.PRIMAL_NETHERITE, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Primal netherite tier weapons
    public static final Item PRIMAL_NETHERITE_SWORD = registerItem("PRIMAL_NETHERITE_SWORD", new SwordItem(PToolMaterials.PRIMAL_NETHERITE, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADEABLE));

    // Doesn't work
    public static HoeItem createHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        HoeItem item = new HoeItem(material, (int) attackDamage, attackSpeed, settings);
        item.attackDamage = attackDamage;
        return item;
    }

    private static Item registerItem(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, List<TagKey<Item>> tags) {
        id = id.toLowerCase();
        String name = StringUtils.toNormalCase(id);
        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), item);
        data.put(registeredItem, new ItemData(name, ms -> modelSupplier.accept(ms, registeredItem), tags));
        return registeredItem;
    }

    private static Item registerItem(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier) {
        return registerItem(id, item, modelSupplier, null);
    }

    public static void init(){
        Prog.LOGGER.info("Registering Mod Items for: " + Prog.MOD_ID);
    }
}
