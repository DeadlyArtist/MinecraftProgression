package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityTickEvents {
    // Called before entity tick
    public static final Event<EntityTickEvents.EntityTick> ENTITY_TICK = EventFactory.createArrayBacked(EntityTickEvents.EntityTick.class, callbacks -> (entity) -> {
        for (EntityTickEvents.EntityTick callback : callbacks) {
            callback.tick(entity);
        }
    });

    public static final Event<EntityTickEvents.LivingEntityTick> LIVING_ENTITY_TICK = EventFactory.createArrayBacked(EntityTickEvents.LivingEntityTick.class, callbacks -> (entity) -> {
        for (EntityTickEvents.LivingEntityTick callback : callbacks) {
            callback.tick(entity);
        }
    });

    public static final Event<EntityTickEvents.PlayerEntityTick> PLAYER_ENTITY_TICK = EventFactory.createArrayBacked(EntityTickEvents.PlayerEntityTick.class, callbacks -> (entity) -> {
        for (EntityTickEvents.PlayerEntityTick callback : callbacks) {
            callback.tick(entity);
        }
    });

    @FunctionalInterface
    public interface EntityTick {
        void tick(Entity entity);
    }

    @FunctionalInterface
    public interface LivingEntityTick {
        void tick(LivingEntity entity);
    }

    @FunctionalInterface
    public interface PlayerEntityTick {
        void tick(PlayerEntity entity);
    }
}
