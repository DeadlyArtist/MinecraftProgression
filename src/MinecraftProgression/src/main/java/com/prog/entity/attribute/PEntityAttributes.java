package com.prog.entity.attribute;

import com.prog.Prog;
import com.prog.utils.StringUtils;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
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
