package com.prog.entity.attribute;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.fabricmc.fabric.mixin.object.builder.DefaultAttributeRegistryAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

public class PDefaultAttributes {
    public static void register(EntityType<? extends LivingEntity> type, DefaultAttributeContainer container) {
        DefaultAttributeRegistryAccessor.getRegistry().put(type, container);
    }

    public static void init() {
        LOGGER.info("Registering Default Attributes for: " + Prog.MOD_ID);
    }
}
