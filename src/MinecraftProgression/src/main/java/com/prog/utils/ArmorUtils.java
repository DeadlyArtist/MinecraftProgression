package com.prog.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;

public class ArmorUtils {

    public static float applyArmorToDamage(LivingEntity entity, float damage) {
        var armor = entity.getArmor();
        var toughness = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        var hitpct = Math.max(0, Math.min(1, damage / entity.getMaxHealth()));

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // damage = 1
        // armor = 10
        // offset = 25
        // formula1 = damage * offset / (offset + armor)
        // formula2 = damage * 1 / (1 + armor / 10);
        // [formula1, formula2].join("    ")
        var offset = 25;
        damage = damage * ((float) offset / (armor + offset));

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // damage = 1
        // toughness = 10
        // hitpct = 0.25 // (0-1) relative health lost
        // formula1 = damage * (1 / (toughness / 10 + 1) * hitpct + (1 - hitpct));
        // [formula1].join("    ")
        damage = damage * (1 / (toughness / 10 + 1) * hitpct + (1 - hitpct));

        return Math.max(0.5f, damage);
    }
}
