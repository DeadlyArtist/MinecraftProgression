package com.prog.utils;

import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class SilentUtils {
    public static boolean isSilent(LivingEntity entity) {
        var disabled = false;
        if (entity instanceof PlayerEntity player) disabled = PComponents.PLAYER.get(player).silentDisabled;
        return entity.getAttributeValue(PEntityAttributes.SILENT) == 1 && !disabled;
    }
}
