package com.prog.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.event.EntityEvents;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.UseUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

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
        return divisor == 16 ? 0 : MathHelper.ceil(Math.max(0, originalReturnValue - reduction) / divisor);
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

    @Inject(at = @At("TAIL"), method = "dropLoot")
    private void dropBonusLoot(DamageSource source, boolean causedByPlayer, CallbackInfo info) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!causedByPlayer || !(self instanceof MobEntity entity)) return;
        var squad = PComponents.SQUAD.get(entity);
        if (squad.normal()) return;
        MinecraftServer server = self.world.getServer();
        if (server == null) return;

        var maxEnchantmentLevel = EnchantmentUtils.MAX_ENCHANTMENT_LEVEL;
        var amount = Math.max(1, squad.rank + 1 - maxEnchantmentLevel);
        var enchantmentLevel = Math.min(maxEnchantmentLevel, squad.rank);
        var excludedEnchantments = new HashSet<>(List.of(Enchantments.MENDING, Enchantments.UNBREAKING));
        for (var i = 0; i < amount; i++) {
            var stack = EnchantedBookItem.forEnchantment(EnchantmentUtils.getRandomEnchantmentLevelEntry(self.random, enchantmentLevel, false, excludedEnchantments));
            self.dropStack(stack);
        }
    }

    @Inject(method = "applyArmorToDamage", at = @At("HEAD"), cancellable = true)
    private void injectApplyArmorToDamage(DamageSource damageSource, float damage, CallbackInfoReturnable<Float> cir) {
        if (damageSource.bypassesArmor()) return;
        damageArmor(damageSource, damage);

        var self = (LivingEntity) (Object) this;
        var armor = self.getArmor();
        var toughness = (float) self.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        var hitpct = Math.max(0, Math.min(1, damage / self.getMaxHealth()));

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // damage = 1
        // armor = 10
        // offset = 25
        // formula1 = damage * offset / (offset + armor)
        // formula2 = damage * 1 / (1 + armor / 10);
        // [formula1, formula2].join("    ")
        var offset = 25;
        damage = damage * ((float) offset / (armor + offset));

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // damage = 1
        // toughness = 10
        // hitpct = 0.25 // (0-1) relative health lost
        // formula1 = damage * (1 / (toughness / 10 + 1) * hitpct + (1 - hitpct));
        // [formula1, formula2].join("    ")
        damage = damage * (1 / (toughness / 10 + 1) * hitpct + (1 - hitpct));

        cir.setReturnValue(Math.max(0.5f, damage));
    }
}