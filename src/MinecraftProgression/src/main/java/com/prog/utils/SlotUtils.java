package com.prog.utils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class SlotUtils {
    public static final int SIZE = 18;
    public static final int HALF_SIZE = SIZE / 2;

    public static boolean isArmorSlot(EquipmentSlot slot) {
        return slot.getType() == EquipmentSlot.Type.ARMOR;
    }

    public static boolean isHandSlot(EquipmentSlot slot) {
        return slot.getType() == EquipmentSlot.Type.HAND;
    }

    public static boolean isEquipped(ItemStack itemStack, EquipmentSlot slot) {
        return slot == LivingEntity.getPreferredEquipmentSlot(itemStack);
    }
}
