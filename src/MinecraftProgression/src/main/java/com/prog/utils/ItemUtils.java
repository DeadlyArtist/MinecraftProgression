package com.prog.utils;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemUtils {
    public static Item byId(Identifier id) {
        return Registry.ITEM.get(id);
    }

    public static Item byId(String id) {
        return byId(new Identifier(id));
    }

    public static Identifier getId(Item item) {
        return Registry.ITEM.getId(item);
    }

    public static boolean hasTag(Item item, TagKey<Item> tag) {
        return item.getRegistryEntry().isIn(tag);
    }

    public static boolean isArmor(Item item) {
        return item instanceof ArmorItem;
    }

    public static boolean isTool(Item item) {
        return item instanceof ToolItem;
    }

    public static boolean isRanged(Item item) {
        return isBowlike(item) || isTrident(item);
    }

    public static boolean isBowlike(Item item) {
        return item instanceof RangedWeaponItem;
    }

    public static boolean isTrident(Item item) {
        return item instanceof TridentItem;
    }

    public static boolean isMelee(Item item) {
        return !isRanged(item) && !isArmor(item);
    }

    public static boolean isBoots(Item item) {
        return isArmor(item) && ((ArmorItem) item).getSlotType() == EquipmentSlot.FEET;
    }

    public static boolean isHelmet(Item item) {
        return isArmor(item) && ((ArmorItem) item).getSlotType() == EquipmentSlot.HEAD;
    }

    public static boolean isChestplate(Item item) {
        return isArmor(item) && ((ArmorItem) item).getSlotType() == EquipmentSlot.CHEST;
    }

    public static boolean isLeggings(Item item) {
        return isArmor(item) && ((ArmorItem) item).getSlotType() == EquipmentSlot.LEGS;
    }

    public static boolean isAxe(Item item) {
        return item instanceof AxeItem;
    }

    public static boolean isHoe(Item item) {
        return item instanceof HoeItem;
    }

    public static boolean isPickaxe(Item item) {
        return item instanceof PickaxeItem;
    }

    public static boolean isShovel(Item item) {
        return item instanceof ShovelItem;
    }

    public static boolean isSword(Item item) {
        return item instanceof SwordItem;
    }

    public static boolean isBow(Item item) {
        return item instanceof BowItem;
    }

    public static boolean isCrossbow(Item item) {
        return item instanceof CrossbowItem;
    }

    public static boolean isFishingRod(Item item) {
        return item instanceof FishingRodItem;
    }
}
