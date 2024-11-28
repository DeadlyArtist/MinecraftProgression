package com.prog.utils;

import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;

public class ElytraUtils {
    public static boolean canUse(PlayerEntity player, ItemStack stack) {
        return stack.getItem() instanceof ElytraItem || (player.getAttributeValue(PEntityAttributes.ELYTRA) == 1 && !PComponents.PLAYER.get(player).elytraDisabled);
    }
}
