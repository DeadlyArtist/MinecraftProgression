package com.prog.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.prog.enchantment.PEnchantments;
import com.prog.event.EntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerEntity.class, priority = 2000)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        EntityEvents.PLAYER_ENTITY_TICK.invoker().tick(entity);
    }

    // Function adapted from https://github.com/pauverblom/flight-affinity/blob/1.20.x/src/main/java/net/baneina/flightaffinity/mixin/PlayerEntityMixin.java
    @ModifyExpressionValue(method = "getBlockBreakingSpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;onGround:Z", opcode = Opcodes.GETFIELD))
    public boolean flightAffinityEnchantmentAndIsOnGround(boolean originalIsOnGround) {
        // Check for the presence of the custom 'Air Affinity' enchantment
        boolean returnValue = originalIsOnGround;
        if (!originalIsOnGround && EnchantmentHelper.getLevel(PEnchantments.AIR_AFFINITY, this.getEquippedStack(EquipmentSlot.HEAD)) > 0) {
            returnValue = true; // If player is in the air and has Air Affinity, treat as on ground
        }
        return returnValue;
    }

    @Inject(at = @At("HEAD"), method = "getNextLevelExperience", cancellable = true)
    private void getNextLevelExperience(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(30);
        info.cancel();
    }
}