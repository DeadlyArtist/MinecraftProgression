package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class EntityEvents {
    // Called before entity tick
    public static final Event<IEntityTick> ENTITY_TICK = EventFactory.createArrayBacked(IEntityTick.class, callbacks -> (entity) -> {
        for (IEntityTick callback : callbacks) {
            callback.tick(entity);
        }
    });

    public static final Event<ILivingEntityTick> LIVING_ENTITY_TICK = EventFactory.createArrayBacked(ILivingEntityTick.class, callbacks -> (entity) -> {
        for (ILivingEntityTick callback : callbacks) {
            callback.tick(entity);
        }
    });

    public static final Event<IPlayerEntityTick> PLAYER_ENTITY_TICK = EventFactory.createArrayBacked(IPlayerEntityTick.class, callbacks -> (entity) -> {
        for (IPlayerEntityTick callback : callbacks) {
            callback.tick(entity);
        }
    });

    public static final Event<ICreateLivingAttributes> CREATE_LIVING_ATTRIBUTES = EventFactory.createArrayBacked(ICreateLivingAttributes.class, callbacks -> (builder) -> {
        for (ICreateLivingAttributes callback : callbacks) {
            callback.create(builder);
        }
    });

    public static final Event<IApplyFoodEffects> APPLY_FOOD_EFFECTS = EventFactory.createArrayBacked(IApplyFoodEffects.class, callbacks -> (entity, stack) -> {
        for (IApplyFoodEffects callback : callbacks) {
            callback.apply(entity, stack);
        }
    });

    public static final Event<IReadCustomDataFromNbt> READ_CUSTOM_DATA_FROM_NBT = EventFactory.createArrayBacked(IReadCustomDataFromNbt.class, callbacks -> (entity, nbt) -> {
        for (IReadCustomDataFromNbt callback : callbacks) {
            callback.read(entity, nbt);
        }
    });

    public static final Event<IWriteCustomDataToNbt> WRITE_CUSTOM_DATA_TO_NBT = EventFactory.createArrayBacked(IWriteCustomDataToNbt.class, callbacks -> (entity, nbt) -> {
        for (IWriteCustomDataToNbt callback : callbacks) {
            callback.write(entity, nbt);
        }
    });

    @FunctionalInterface
    public interface IEntityTick {
        void tick(Entity entity);
    }

    @FunctionalInterface
    public interface ILivingEntityTick {
        void tick(LivingEntity entity);
    }

    @FunctionalInterface
    public interface IPlayerEntityTick {
        void tick(PlayerEntity entity);
    }

    @FunctionalInterface
    public interface ICreateLivingAttributes {
        void create(DefaultAttributeContainer.Builder builder);
    }

    @FunctionalInterface
    public interface IApplyFoodEffects {
        void apply(LivingEntity enity, ItemStack stack);
    }

    @FunctionalInterface
    public interface IReadCustomDataFromNbt {
        void read(LivingEntity entity, NbtCompound nbt);
    }

    @FunctionalInterface
    public interface IWriteCustomDataToNbt {
        void write(LivingEntity entity, NbtCompound nbt);
    }
}
