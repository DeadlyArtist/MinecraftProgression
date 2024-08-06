package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Steel
    public static final Item STEEL_INGOT = registerItem("STEEL_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_BINDING = registerItem("STEEL_BINDING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Steel tier armor
    public static final Item STEEL_BOOTS = registerItem("STEEL_BOOTS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item STEEL_CHESTPLATE = registerItem("STEEL_CHESTPLATE", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item STEEL_HELMET = registerItem("STEEL_HELMET", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item STEEL_LEGGINGS = registerItem("STEEL_LEGGINGS", new ArmorItem(PArmorMaterials.STEEL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Steel tier tools
    public static final Item STEEL_AXE = registerItem("STEEL_AXE", new AxeItem(PToolMaterials.STEEL, 5.0F, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item STEEL_HOE = registerItem("STEEL_HOE", new HoeItem(PToolMaterials.STEEL, -2, -1.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item STEEL_PICKAXE = registerItem("STEEL_PICKAXE", new PickaxeItem(PToolMaterials.STEEL, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item STEEL_SHOVEL = registerItem("STEEL_SHOVEL", new ShovelItem(PToolMaterials.STEEL, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Steel tier weapons
    public static final Item STEEL_SWORD = registerItem("STEEL_SWORD", new SwordItem(PToolMaterials.STEEL, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));


    // Ultimate diamond


    // Ultimate diamond tier armor
    public static final Item ULTIMATE_DIAMOND_BOOTS = registerItem("ULTIMATE_DIAMOND_BOOTS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item ULTIMATE_DIAMOND_CHESTPLATE = registerItem("ULTIMATE_DIAMOND_CHESTPLATE", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item ULTIMATE_DIAMOND_HELMET = registerItem("ULTIMATE_DIAMOND_HELMET", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item ULTIMATE_DIAMOND_LEGGINGS = registerItem("ULTIMATE_DIAMOND_LEGGINGS", new ArmorItem(PArmorMaterials.ULTIMATE_DIAMOND, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Ultimate diamond tier tools
    public static final Item ULTIMATE_DIAMOND_AXE = registerItem("ULTIMATE_DIAMOND_AXE", new AxeItem(PToolMaterials.ULTIMATE_DIAMOND, 5.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item ULTIMATE_DIAMOND_HOE = registerItem("ULTIMATE_DIAMOND_HOE", new HoeItem(PToolMaterials.ULTIMATE_DIAMOND, -3, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item ULTIMATE_DIAMOND_PICKAXE = registerItem("ULTIMATE_DIAMOND_PICKAXE", new PickaxeItem(PToolMaterials.ULTIMATE_DIAMOND, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item ULTIMATE_DIAMOND_SHOVEL = registerItem("ULTIMATE_DIAMOND_SHOVEL", new ShovelItem(PToolMaterials.ULTIMATE_DIAMOND, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Ultimate diamond tier weapons
    public static final Item ULTIMATE_DIAMOND_SWORD = registerItem("ULTIMATE_DIAMOND_SWORD", new SwordItem(PToolMaterials.ULTIMATE_DIAMOND, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));


    // Refined obsidian
    public static final Item REFINED_OBSIDIAN_INGOT = registerItem("REFINED_OBSIDIAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item REFINED_OBSIDIAN_MODULE = registerItem("REFINED_OBSIDIAN_MODULE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Refined obsidian tier armor
    public static final Item REFINED_OBSIDIAN_BOOTS = registerItem("REFINED_OBSIDIAN_BOOTS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item REFINED_OBSIDIAN_CHESTPLATE = registerItem("REFINED_OBSIDIAN_CHESTPLATE", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item REFINED_OBSIDIAN_HELMET = registerItem("REFINED_OBSIDIAN_HELMET", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item REFINED_OBSIDIAN_LEGGINGS = registerItem("REFINED_OBSIDIAN_LEGGINGS", new ArmorItem(PArmorMaterials.REFINED_OBSIDIAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Refined obsidian tier tools
    public static final Item REFINED_OBSIDIAN_AXE = registerItem("REFINED_OBSIDIAN_AXE", new AxeItem(PToolMaterials.REFINED_OBSIDIAN, 6.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item REFINED_OBSIDIAN_HOE = registerItem("REFINED_OBSIDIAN_HOE", new HoeItem(PToolMaterials.REFINED_OBSIDIAN, -4, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item REFINED_OBSIDIAN_PICKAXE = registerItem("REFINED_OBSIDIAN_PICKAXE", new PickaxeItem(PToolMaterials.REFINED_OBSIDIAN, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item REFINED_OBSIDIAN_SHOVEL = registerItem("REFINED_OBSIDIAN_SHOVEL", new ShovelItem(PToolMaterials.REFINED_OBSIDIAN, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Refined obsidian tier weapons
    public static final Item REFINED_OBSIDIAN_SWORD = registerItem("REFINED_OBSIDIAN_SWORD", new SwordItem(PToolMaterials.REFINED_OBSIDIAN, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Titan
    public static final Item EMBERITE = registerItem("EMBERITE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item COMPRESSED_QUARTZ = registerItem("COMPRESSED_QUARTZ", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item TITAN_INGOT = registerItem("TITAN_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item TITAN_CORE = registerItem("TITAN_CORE", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Titan tier armor
    public static final Item TITAN_BOOTS = registerItem("TITAN_BOOTS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item TITAN_CHESTPLATE = registerItem("TITAN_CHESTPLATE", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item TITAN_HELMET = registerItem("TITAN_HELMET", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item TITAN_LEGGINGS = registerItem("TITAN_LEGGINGS", new ArmorItem(PArmorMaterials.TITAN, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Titan tier tools
    public static final Item TITAN_AXE = registerItem("TITAN_AXE", new AxeItem(PToolMaterials.TITAN, 6.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item TITAN_HOE = registerItem("TITAN_HOE", new HoeItem(PToolMaterials.TITAN, -4, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item TITAN_PICKAXE = registerItem("TITAN_PICKAXE", new PickaxeItem(PToolMaterials.TITAN, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item TITAN_SHOVEL = registerItem("TITAN_SHOVEL", new ShovelItem(PToolMaterials.TITAN, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Titan tier weapons
    public static final Item TITAN_SWORD = registerItem("TITAN_SWORD", new SwordItem(PToolMaterials.TITAN, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));


    // Primal netherite tier armor
    public static final Item PRIMAL_NETHERITE_BOOTS = registerItem("PRIMAL_NETHERITE_BOOTS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item PRIMAL_NETHERITE_CHESTPLATE = registerItem("PRIMAL_NETHERITE_CHESTPLATE", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item PRIMAL_NETHERITE_HELMET = registerItem("PRIMAL_NETHERITE_HELMET", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item PRIMAL_NETHERITE_LEGGINGS = registerItem("PRIMAL_NETHERITE_LEGGINGS", new ArmorItem(PArmorMaterials.PRIMAL_NETHERITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Primal netherite tier tools
    public static final Item PRIMAL_NETHERITE_AXE = registerItem("PRIMAL_NETHERITE_AXE", new AxeItem(PToolMaterials.PRIMAL_NETHERITE, 7.0F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item PRIMAL_NETHERITE_HOE = registerItem("PRIMAL_NETHERITE_HOE", new HoeItem(PToolMaterials.PRIMAL_NETHERITE, -6, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item PRIMAL_NETHERITE_PICKAXE = registerItem("PRIMAL_NETHERITE_PICKAXE", new PickaxeItem(PToolMaterials.PRIMAL_NETHERITE, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));
    public static final Item PRIMAL_NETHERITE_SHOVEL = registerItem("PRIMAL_NETHERITE_SHOVEL", new ShovelItem(PToolMaterials.PRIMAL_NETHERITE, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Primal netherite tier weapons
    public static final Item PRIMAL_NETHERITE_SWORD = registerItem("PRIMAL_NETHERITE_SWORD", new SwordItem(PToolMaterials.PRIMAL_NETHERITE, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), List.of(PItemTags.UPGRADABLE));

    // Upgrades
    public static final Item MECHANICAL_BOOTS = registerUpgrade("MECHANICAL_BOOTS", new ArmorItem(PArmorMaterials.MECHANICAL_BOOTS, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), UEffectMapper.boots(UEffect.increment(PEntityAttributes.STEP_HEIGHT)));
    public static final Item ANGEL_RING = registerUpgrade("ANGEL_RING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED), UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.FLIGHT)));

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

    private static Item registerUpgrade(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, List<TagKey<Item>> tags, Function<Item, List<UEffect>> effects) {
        tags = new ArrayList<>(tags);
        tags.add(PItemTags.UPGRADE);
        Item registeredItem = registerItem(id, item, modelSupplier, tags);
        Upgrades.register(registeredItem, effects);
        return registeredItem;
    }

    private static Item registerUpgrade(String id, Item item, BiConsumer<ItemModelGenerator, Item> modelSupplier, Function<Item, List<UEffect>> effects) {
        return registerUpgrade(id, item, modelSupplier, List.of(), effects);
    }

    public static void init(){
        Prog.LOGGER.info("Registering Items for: " + Prog.MOD_ID);
    }
}
