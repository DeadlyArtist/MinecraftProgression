package com.prog.entity.attribute;

import com.prog.Prog;
import net.fabricmc.fabric.mixin.object.builder.DefaultAttributeRegistryAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;

public class PDefaultAttributes {
    public static void register(EntityType<? extends LivingEntity> type, DefaultAttributeContainer container) {
        DefaultAttributeRegistryAccessor.getRegistry().put(type, container);
    }

    public static void init() {
        Prog.LOGGER.info("Registering Default Attributes for: " + Prog.MOD_ID);
    }
}
