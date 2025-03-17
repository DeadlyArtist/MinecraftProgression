package com.prog.utils;

public class XpUtils {
    public static int getDroppedXp(int experience, int lootingLevel) {
        return (int) (experience * (1 + lootingLevel * 0.2));
    }
}
