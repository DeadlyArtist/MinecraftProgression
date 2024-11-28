package com.prog.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.enchantment.PEnchantments;
import com.prog.event.EntityEvents;
import com.prog.utils.ElytraUtils;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.SilentUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerEntity.class, priority = 2000)
public abstract class PlayerEntityMixin extends LivingEntity {
    public PlayerEntity self = (PlayerEntity) (Object) this;

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        EntityEvents.PLAYER_ENTITY_TICK.invoker().tick(entity);
    }

    @Inject(method = "getMoveEffect", at = @At("HEAD"), cancellable = true)
    protected void getMoveEffect(CallbackInfoReturnable<MoveEffect> cir) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        if (SilentUtils.isSilent(entity)) cir.setReturnValue(MoveEffect.NONE);
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

    @Inject(method = "getNextLevelExperience", at = @At("HEAD"), cancellable = true)
    private void getNextLevelExperience(CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(30);
        info.cancel();
    }

    @Inject(
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F",
                    shift = At.Shift.BEFORE
            )
    )
    private void redirectGetAttackDamage(Entity target, CallbackInfo ci, @Local(ordinal = 0) float f, @Local(ordinal = 1) LocalFloatRef g) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        var group = target instanceof LivingEntity livingEntity ? livingEntity.getGroup() : EntityGroup.DEFAULT;
        g.set((float) EnchantmentUtils.getAttackDamageIncrease(group, this.getMainHandStack(), f));
    }

    @Redirect(method = "disableShield", at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/ItemCooldownManager;set(Lnet/minecraft/item/Item;I)V"
    ))
    public void disableShield(ItemCooldownManager instance, Item item, int duration) {
        Item activeItem = this.activeItemStack.getItem();
        instance.set(activeItem instanceof ShieldItem ? activeItem : Items.SHIELD, duration);
    }

    @Redirect(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return ElytraUtils.canUse(self, instance);
    }
}