package com.prog.mixin;

import com.prog.entity.PComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageSource.class) // This targets the DamageSource class
public abstract class DamageSourceMixin {

    @Redirect(method = "getDeathMessage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDisplayName()Lnet/minecraft/text/Text;", ordinal = 1))
    private Text redirectGetDisplayName(LivingEntity entity) {
        Text originalDisplayName = entity.getDisplayName();
        var squad = PComponents.SQUAD.get(entity);
        if (!squad.normal()) return Text.literal(entity.getType().getName() + " " + entity.getDisplayName());
        return originalDisplayName;
    }
}
