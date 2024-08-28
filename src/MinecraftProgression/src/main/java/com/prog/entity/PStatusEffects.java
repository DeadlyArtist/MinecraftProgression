package com.prog.entity;

import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.itemOrBlock.PItemGroups;
import com.prog.utils.StringUtils;
import com.prog.utils.UUIDUtils;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class PStatusEffects {
    public static class StatusEffectData {
        public String name;

        public StatusEffectData(String name) {
            this.name = name;
        }
    }

    public static final Map<StatusEffect, StatusEffectData> data = new HashMap<>();

    public static final StatusEffect WITHER_IMMUNITY = register("WITHER_IMMUNITY", new StatusEffect(StatusEffectCategory.HARMFUL, 4738376).addAttributeModifier(PEntityAttributes.WITHER_IMMUNITY, UUIDUtils.of("WITHER_IMMUNITY").toString(), 1.0, EntityAttributeModifier.Operation.ADDITION)
    );


    private static StatusEffect register(String id, StatusEffect entry) {
        var statusEffect = Registry.register(Registry.STATUS_EFFECT, Identifier.of(Prog.MOD_ID, id.toLowerCase()), entry);
        data.put(statusEffect, new StatusEffectData(StringUtils.toNormalCase(id)));
        return statusEffect;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Status Effects for: " + Prog.MOD_ID);
    }
}
