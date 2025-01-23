package com.prog.mixin;

import com.prog.utils.WitherUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.WitherSkullEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WitherSkullEntity.class)
public class WitherSkullEntityMixin {
    @Unique
    private final WitherSkullEntity self = (WitherSkullEntity) (Object) this;

    @ModifyConstant(method = "onEntityHit", constant = @Constant(floatValue = 8, ordinal = 0))
    private float changeDamage(float constant) {
        var owner = (LivingEntity) self.getOwner();
        return (float) owner.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @ModifyConstant(method = "onEntityHit", constant = @Constant(floatValue = 5, ordinal = 0))
    private float changeHealing(float constant) {
        return WitherUtils.SKULL_HEALING;
    }
}
