package com.prog.utils;

import com.prog.Prog;
import com.prog.itemOrBlock.UEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.List;
import java.util.UUID;

public class EntityAttributeModifierUtils {
    public static EntityAttributeModifier of(String name, double value, EntityAttributeModifier.Operation operation) {
        if (name.isEmpty()) return new EntityAttributeModifier(name, value, operation);
        else return new EntityAttributeModifier(UUIDUtils.of(name), name, value, operation);
    }

    public static EntityAttributeModifier of(double value, EntityAttributeModifier.Operation operation, boolean randomName) {
        return of(randomName ? String.join("___", List.of(StringUtils.random(), Prog.MOD_ID, String.valueOf(value), String.valueOf(operation.getId()))): "", value, operation);
    }

    public static EntityAttributeModifier of(double value, EntityAttributeModifier.Operation operation) {
        return of(value, operation, false);
    }

    public static EntityAttributeModifier add(double value) {
        return of(value, EntityAttributeModifier.Operation.ADDITION);
    }

    public static EntityAttributeModifier increment() {
        return add(1);
    }

    public static EntityAttributeModifier add(String name, double value) {
        return of(name, value, EntityAttributeModifier.Operation.ADDITION);
    }

    public static EntityAttributeModifier increment(String name) {
        return add(name, 1);
    }
}
