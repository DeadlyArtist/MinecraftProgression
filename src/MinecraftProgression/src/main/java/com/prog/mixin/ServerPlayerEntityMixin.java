package com.prog.mixin;

import com.prog.criterion.PCriteria;
import com.prog.entity.PComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Unique
    private final ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;

    @Inject(method = "increaseStat", at = @At("HEAD"))
    private void onIncreaseStat(Stat<?> stat, int amount, CallbackInfo ci) {
        if (stat.getValue().equals(Stats.EAT_CAKE_SLICE)) {
            PComponents.LIVING_ENTITY.get(player).eat(Items.CAKE);
        }
    }

    @Inject(method = "updateKilledAdvancementCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/OnKilledCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void onKill(Entity entityKilled, int score, DamageSource damageSource, CallbackInfo ci) {
        if (entityKilled instanceof MobEntity mob) {
            PCriteria.DEFEAT_RANK.trigger(player, mob);
        }
    }
}
