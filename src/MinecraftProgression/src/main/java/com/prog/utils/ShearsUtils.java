package com.prog.utils;

import com.prog.itemOrBlock.custom.TieredShearsItem;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.random.Random;

public class ShearsUtils {


    public static int getWoolAmount(SheepEntity entity, Item shearsItem) {
        var woolBonus = 0;
        if (shearsItem instanceof TieredShearsItem tiered) woolBonus = tiered.material.getWoolBonus();
        int amount = 1 + entity.random.nextInt(3 + woolBonus) + woolBonus;
        return amount;
    }
}
