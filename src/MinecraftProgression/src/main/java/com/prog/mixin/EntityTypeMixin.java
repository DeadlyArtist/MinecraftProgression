package com.prog.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(
            method = "register",
            at = @At("HEAD")
    )
    private static <T extends Entity> void injectMakeFishingBobberFireImmune(String id, EntityType.Builder<T> type, CallbackInfoReturnable<EntityType<T>> cir) {
        if ("fishing_bobber".equals(id)) {
            type.makeFireImmune();
        }
    }
}
