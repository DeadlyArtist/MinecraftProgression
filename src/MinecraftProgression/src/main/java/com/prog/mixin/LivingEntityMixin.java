package com.prog.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.event.EntityEvents;
import com.prog.utils.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private final LivingEntity self = (LivingEntity)(Object)this;

    @Shadow public abstract void damageArmor(DamageSource source, float amount);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Unique
    private int ticksSince = 0;


    @Inject(
            method = "setCurrentHand",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getMaxUseTime()I",
                    shift = At.Shift.BEFORE
            )
    )
    private void redirectGetMaxUseTimeInSetCurrentHand(Hand hand, CallbackInfo ci) {
        ticksSince = 0;
    }

    @Inject(
            method = "onTrackedDataSet",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getMaxUseTime()I",
                    shift = At.Shift.BEFORE
            )
    )
    private void redirectGetMaxUseTimeInOnTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        ticksSince = 0;
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        EntityEvents.LIVING_ENTITY_TICK.invoker().tick(entity);

        if (entity.isUsingItem()) {
            ticksSince++;
            UseUtils.handleItemUseProgress(entity, ticksSince);
        }
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void createLivingAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        EntityEvents.CREATE_LIVING_ATTRIBUTES.invoker().create(info.getReturnValue());
    }

    @ModifyReturnValue(
            method = "computeFallDamage",
            at = @At("RETURN")
    )
    private int computeFallDamage(int originalReturnValue, float fallDistance, float damageMultiplier) {
        LivingEntity self = (LivingEntity) (Object) this;
        double divisor = self.getAttributeValue(PEntityAttributes.LIGHTNESS);
        double reduction = self.getAttributeValue(PEntityAttributes.IMPACT_ABSORPTION);
        return divisor == 0 ? 0 : MathHelper.ceil(Math.max(0, originalReturnValue - reduction) / divisor);
    }

    @Inject(method = "applyFoodEffects", at = @At("HEAD"))
    private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo info) {
        Item item = stack.getItem();
        if (!item.isFood()) return;

        EntityEvents.APPLY_FOOD_EFFECTS.invoker().apply(targetEntity, stack);
    }

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> info) {
        LivingEntity self = (LivingEntity) (Object) this;
        var can = EntityEvents.CAN_HAVE_STATUS_EFFECT.invoker().check(self, effect);
        if (!can) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Redirect(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropXp()V"))
    private void redirectDropXp(LivingEntity instance, @Local DamageSource source) {
        var entity = source.getAttacker();
        var lootingLevel = 0;
        if (entity instanceof PlayerEntity) lootingLevel = EnchantmentHelper.getLooting((LivingEntity) entity);
        if (self.world instanceof ServerWorld
                && !self.isExperienceDroppingDisabled()
                && (self.shouldAlwaysDropXp() || self.playerHitTimer > 0 && self.shouldDropXp() && self.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
            var amount = self.getXpToDrop();
            if (self instanceof MobEntity mob) amount = SquadUtils.adjustXPDrop(mob, amount);
            amount = XpUtils.getDroppedXp(amount, lootingLevel);
            ExperienceOrbEntity.spawn((ServerWorld) self.world, self.getPos(), amount);
        }
    }

    @Inject(at = @At("TAIL"), method = "dropLoot")
    private void dropBonusLoot(DamageSource source, boolean causedByPlayer, CallbackInfo info) {
        if (!causedByPlayer || !(self instanceof MobEntity entity)) return;
        MinecraftServer server = self.world.getServer();
        if (server == null) return;

        SquadUtils.DropBonusLoot(entity);
    }

    @Inject(method = "applyArmorToDamage", at = @At("HEAD"), cancellable = true)
    private void injectApplyArmorToDamage(DamageSource damageSource, float damage, CallbackInfoReturnable<Float> cir) {
        if (damageSource.bypassesArmor()) return;
        damageArmor(damageSource, damage);

        cir.setReturnValue(ArmorUtils.applyArmorToDamage(self, damage));
    }

    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 0, ordinal = 0))
    private float changeDamageBlocking(float constant, @Local DamageSource source, @Local(ordinal = 0) LocalFloatRef amountRef) {
        var self = (LivingEntity) (Object) this;

        var oldAmount = amountRef.get();
        var newAmount = ShieldUtils.applyShieldToDamage(self, source, oldAmount);
        amountRef.set(newAmount);
        var blocked = oldAmount - newAmount;

        return blocked;
    }

    @Redirect(method = "tickFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        if (self instanceof PlayerEntity player) return ElytraUtils.canUse(player, instance);
        return instance.getItem() instanceof ElytraItem;
    }
}