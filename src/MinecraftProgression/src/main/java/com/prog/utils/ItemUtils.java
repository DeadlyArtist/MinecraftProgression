package com.prog.utils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public class ItemUtils {
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
