package com.prog.itemOrBlock;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.prog.utils.EntityAttributeModifierUtils;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class UEffect {
    public final EntityAttribute target;
    public final EntityAttributeModifier modifier;

    public UEffect(EntityAttribute target, EntityAttributeModifier modifier) {
        this.target = target;
        this.modifier = modifier;
    }

    public static UEffect of(EntityAttribute target, EntityAttributeModifier modifier) {
        return new UEffect(target, modifier);
    }

    public static UEffect of(EntityAttribute target, EntityAttributeModifier.Operation operation, double value) {
        return of(target, EntityAttributeModifierUtils.of(value, operation));
    }

    public static UEffect add(EntityAttribute target, double value) {
        return of(target, EntityAttributeModifierUtils.add(value));
    }

    public static UEffect increment(EntityAttribute target) {
        return of(target, EntityAttributeModifierUtils.increment());
    }


    public static UEffect of(EntityAttribute target, String name, EntityAttributeModifier.Operation operation, double value) {
        return of(target, EntityAttributeModifierUtils.of(name, value, operation));
    }

    public static UEffect add(EntityAttribute target, String name, double value) {
        return of(target, EntityAttributeModifierUtils.add(name, value));
    }

    public static UEffect increment(EntityAttribute target, String name) {
        return of(target, EntityAttributeModifierUtils.increment(name));
    }

    public static UEffect fromUpgradeNbt(String name, NbtCompound nbt) {
        var targetId = nbt.getString("target");
        var target = Registry.ATTRIBUTE.get(new Identifier(targetId));
        var modifierNbt = nbt.getCompound("modifier");
        var value = modifierNbt.getDouble("value");
        var operation = EntityAttributeModifier.Operation.fromId(nbt.getInt("operation"));
        var effect = UEffect.of(target, EntityAttributeModifierUtils.of(name, value, operation));
        return effect;
    }

    public JsonObject toJson() {
        var json = new JsonObject();
        json.add("target", new JsonPrimitive(Registry.ATTRIBUTE.getId(target).toString()));
        var modifierJson = new JsonObject();
        if (modifier.getName() != "") modifierJson.add("name", new JsonPrimitive(modifier.getName()));
        modifierJson.add("value", new JsonPrimitive(modifier.getValue()));
        modifierJson.add("operation", new JsonPrimitive(modifier.getOperation().getId()));
        json.add("modifier", modifierJson);
        return json;
    }
}