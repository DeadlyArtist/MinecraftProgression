package com.prog.utils;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class UseUtils {
    public static ItemStack getActiveItemStack(LivingEntity entity) {
        return entity.getStackInHand(entity.getActiveHand());
    }

    public static void handleItemUseProgress(LivingEntity entity, int ticksSince) {
        if (!entity.isUsingItem()) return;
        ItemStack stack = getActiveItemStack(entity);
        var left = entity.getItemUseTimeLeft();
        double speed = entity.getAttributeValue(PEntityAttributes.CHARGING_SPEED);
        int adjustedTicks = speed == 1000 ? stack.getMaxUseTime() : (int) (ticksSince * speed);
        entity.itemUseTimeLeft = Math.max(1, stack.getMaxUseTime() - adjustedTicks); // Reduced by 1 before it is checked for 0 equality in living entity tick after this function is called
    }
}
