package com.prog.mixin;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
    @Redirect(method = "spawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z"))
    private boolean redirectIsSpectator(PlayerEntity playerEntity) {
        return playerEntity.isSpectator() || playerEntity.getAttributeValue(PEntityAttributes.INSOMNIA_IMMUNITY) == 1;
    }
}