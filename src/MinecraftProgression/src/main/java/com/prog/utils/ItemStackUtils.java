package com.prog.utils;

import com.prog.entity.attribute.XEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemStack;
import net.projectile_damage.internal.Constants;

import java.util.UUID;

public class ItemStackUtils {
    public static double getAttributeModifierValue(ItemStack stack, EntityAttribute entityAttribute, UUID id) {
        var mod = stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(entityAttribute).stream().filter(m -> m.getId() == id).findAny().orElse(null);
        if (mod != null) return mod.getValue();
        else return 0;
    }
}
