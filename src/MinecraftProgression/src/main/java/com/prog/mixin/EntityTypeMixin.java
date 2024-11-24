package com.prog.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType$Builder;disableSaving()Lnet/minecraft/entity/EntityType$Builder;"
            )
    )
    private static EntityType.Builder<FishingBobberEntity> makeFireImmune(
            EntityType.Builder<FishingBobberEntity> builder
    ) {
        return builder.disableSaving().makeFireImmune();
    }
}
