package com.prog.mixin;

import com.prog.utils.WardenUtils;
import com.prog.utils.WitherUtils;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {
    @Redirect(method = "addAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 0))
    private static DefaultAttributeContainer.Builder changeHealth(DefaultAttributeContainer.Builder instance, EntityAttribute attribute, double baseValue) {
        return instance.add(EntityAttributes.GENERIC_MAX_HEALTH, WardenUtils.WARDEN_MAX_HEALTH);
    }

    @Redirect(method = "addAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 4))
    private static DefaultAttributeContainer.Builder changeDamage(DefaultAttributeContainer.Builder instance, EntityAttribute attribute, double baseValue) {
        return instance.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, WardenUtils.WARDEN_ATTACK_DAMAGE);
    }
}
