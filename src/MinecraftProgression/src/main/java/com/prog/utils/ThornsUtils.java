package com.prog.utils;

import net.minecraft.util.math.random.Random;

public class ThornsUtils {
    public static int getDamage(int level, Random random) {
        return level * (2 + random.nextInt(3)) / 3;
    }
}
