package com.prog.utils;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.LivingEntity;

public class RangedUtils {
    public static float getPullProgress(LivingEntity entity, int useTicks) {
        float f = (float) useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        //f *= (float) entity.getAttributeValue(PEntityAttributes.CHARGING_SPEED); // Would need to be crit speed instead, but doesn't make too much sense.
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
}
