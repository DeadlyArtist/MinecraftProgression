package com.prog.itemOrBlock;

import com.prog.entity.PStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class PFoodComponents {
    public static final FoodComponent GLISTERING_MELON_SLICE = new FoodComponent.Builder().hunger(6).saturationModifier(1.2F).build();
    public static final FoodComponent NETHER_WART = new FoodComponent.Builder().hunger(2).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 600, 1), 1.0F)
            .build();
    public static final FoodComponent ENCHANTED_STAR_APPLE = new FoodComponent.Builder()
            .hunger(4)
            .saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1000, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1000, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(PStatusEffects.WITHER_IMMUNITY, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 12), 1.0F)
            .alwaysEdible()
            .build();
    public static final FoodComponent STAR_APPLE = new FoodComponent.Builder()
            .hunger(6)
            .saturationModifier(2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1000, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 500, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(PStatusEffects.WITHER_IMMUNITY, 3000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 8), 1.0F)
            .alwaysEdible()
            .build();
}
