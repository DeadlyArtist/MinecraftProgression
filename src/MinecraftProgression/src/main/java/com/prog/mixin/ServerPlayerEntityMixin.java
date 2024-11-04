package com.prog.mixin;

import com.prog.entity.PComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "increaseStat", at = @At("HEAD"))
    private void onIncreaseStat(Stat<?> stat, int amount, CallbackInfo ci) {
        if (stat.getValue().equals(Stats.EAT_CAKE_SLICE)) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            PComponents.LIVING_ENTITY.get(player).eat(Items.CAKE);
        }
    }
}
