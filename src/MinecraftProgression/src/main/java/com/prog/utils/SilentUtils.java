package com.prog.utils;

import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

public class SilentUtils {
    public static boolean isSilent(PlayerEntity player) {
        return player.getAttributeValue(PEntityAttributes.SILENT) == 1 && !PComponents.PLAYER.get(player).silentDisabled;
    }
}
