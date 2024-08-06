package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
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

    public static final Event<ICanHaveStatusEffect> CAN_HAVE_STATUS_EFFECT = EventFactory.createArrayBacked(ICanHaveStatusEffect.class, callbacks -> (entity, stack) -> {
        for (ICanHaveStatusEffect callback : callbacks) {
            if (!callback.check(entity, stack)) return false;
        }
        return true;
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
    public interface ICanHaveStatusEffect {
        boolean check(LivingEntity enity, StatusEffectInstance effect);
    }
}
