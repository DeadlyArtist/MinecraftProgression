package com.prog.utils;

import com.prog.entity.PComponents;
import net.minecraft.entity.boss.WitherEntity;

public class WitherUtils {
    public static int WITHER_MAX_HEALTH = 1500;
    public static int WITHER_ATTACK_DAMAGE = 400;
    public static int EXPLOSION_DAMAGE_MULTIPLIER = 12;
    public static int SKULL_HEALING = 300;

    public static int getAdditionalStarDropAmount(WitherEntity wither) {
        var squad = PComponents.SQUAD.get(wither);
        var stars = (int) Math.pow(2, squad.rank);
        return stars - 1;
    }
}
