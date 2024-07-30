package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.itemOrBlock.tiers.PArmorMaterial;
import com.prog.itemOrBlock.tiers.PToolMaterial;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
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
    // Simple fix of HoeItem constructor being protected
    public static class HoeItem extends net.minecraft.item.HoeItem {
        public HoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
            super(material, attackDamage, attackSpeed, settings);
        }
    }

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




    public static final Item STEEL_INGOT = registerItem("STEEL_INGOT", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_BINDING = registerItem("STEEL_BINDING", new Item(new FabricItemSettings().group(ItemGroup.MISC)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Steel tier armor
    public static final Item STEEL_BOOTS = registerItem("STEEL_BOOTS", new ArmorItem(PArmorMaterial.STEEL, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_CHESTPLATE = registerItem("STEEL_CHESTPLATE", new ArmorItem(PArmorMaterial.STEEL, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_HELMET = registerItem("STEEL_HELMET", new ArmorItem(PArmorMaterial.STEEL, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_LEGGINGS = registerItem("STEEL_LEGGINGS", new ArmorItem(PArmorMaterial.STEEL, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Steel tier tools
    public static final Item STEEL_AXE = registerItem("STEEL_AXE", new AxeItem(PToolMaterial.STEEL, 6.0F, -3.1F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_HOE = registerItem("STEEL_HOE", new HoeItem(PToolMaterial.STEEL, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_PICKAXE = registerItem("STEEL_PICKAXE", new PickaxeItem(PToolMaterial.STEEL, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_SHOVEL = registerItem("STEEL_SHOVEL", new ShovelItem(PToolMaterial.STEEL, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));

    // Steel tier weapons
    public static final Item STEEL_SWORD = registerItem("STEEL_SWORD", new SwordItem(PToolMaterial.STEEL, 3, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT)), (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));


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
