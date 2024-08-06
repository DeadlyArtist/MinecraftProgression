package com.prog.entity.attribute;

import com.prog.Prog;
import com.prog.utils.StringUtils;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class PEntityAttributes {
    public static class EntityAttributeData {
        public String name;

        public EntityAttributeData(String name) {
            this.name = name;
        }
    }

    public static final Map<EntityAttribute, EntityAttributeData> data = new HashMap<>();

    public static final EntityAttribute STEP_HEIGHT = registerClampedEntityAttribute("STEP_HEIGHT", 0.6, 0.0, 1000.0);
    public static final EntityAttribute FALL_DAMAGE_DIVISOR = registerClampedEntityAttribute("FALL_DAMAGE_DIVISOR", 0.0, 0.0, 1000.0); // 0 for immunity
    public static final EntityAttribute LUMINANCE = registerClampedEntityAttribute("LUMINANCE", 0.0, 0.0, 15.0); // 0 for immunity

    // Status effect immunities
    public static final EntityAttribute DARKNESS_IMMUNITY = registerClampedEntityAttribute("DARKNESS_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute POISON_IMMUNITY = registerClampedEntityAttribute("POISON_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute WITHER_IMMUNITY = registerClampedEntityAttribute("WITHER_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute SLOWNESS_IMMUNITY = registerClampedEntityAttribute("SLOWNESS_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute WEAKNESS_IMMUNITY = registerClampedEntityAttribute("WEAKNESS_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute BLINDNESS_IMMUNITY = registerClampedEntityAttribute("BLINDNESS_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute HUNGER_IMMUNITY = registerClampedEntityAttribute("HUNGER_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute MINING_FATIGUE_IMMUNITY = registerClampedEntityAttribute("MINING_FATIGUE_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute NAUSEA_IMMUNITY = registerClampedEntityAttribute("NAUSEA_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute LEVITATION_IMMUNITY = registerClampedEntityAttribute("LEVITATION_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute UNLUCK_IMMUNITY = registerClampedEntityAttribute("UNLUCK_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute BAD_OMEN_IMMUNITY = registerClampedEntityAttribute("BAD_OMEN_IMMUNITY", 0.0, 0.0, 1.0);
    public static final EntityAttribute GLOWING_IMMUNITY = registerClampedEntityAttribute("GLOWING_IMMUNITY", 0.0, 0.0, 1.0);

    public static final Map<StatusEffect, EntityAttribute> IMMUNITY_MAP = new HashMap<>();

    static {
        IMMUNITY_MAP.put(StatusEffects.DARKNESS, DARKNESS_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.POISON, POISON_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.WITHER, WITHER_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.SLOWNESS, SLOWNESS_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.WEAKNESS, WEAKNESS_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.BLINDNESS, BLINDNESS_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.HUNGER, HUNGER_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.MINING_FATIGUE, MINING_FATIGUE_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.NAUSEA, NAUSEA_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.LEVITATION, LEVITATION_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.UNLUCK, UNLUCK_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.BAD_OMEN, BAD_OMEN_IMMUNITY);
        IMMUNITY_MAP.put(StatusEffects.GLOWING, GLOWING_IMMUNITY);
    }

    public static EntityAttribute registerClampedEntityAttribute(String id, double fallback, double min, double max){
        id = id.toLowerCase();
        return register(id, new ClampedEntityAttribute("attribute.name.generic." + id, fallback, min, max).setTracked(true));
    }

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        EntityAttribute entityAttribute = Registry.register(Registry.ATTRIBUTE, new Identifier(Prog.MOD_ID, id.toLowerCase()), attribute);
        data.put(entityAttribute, new EntityAttributeData(StringUtils.toNormalCase(id)));
        return entityAttribute;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Entity Attributes for: " + Prog.MOD_ID);
    }
}