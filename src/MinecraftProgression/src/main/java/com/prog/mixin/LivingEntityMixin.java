package com.prog.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.event.EntityEvents;
import com.prog.utils.ItemUtils;
import com.prog.utils.SlotUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        EntityEvents.LIVING_ENTITY_TICK.invoker().tick(entity);
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
        double divisor = self.getAttributeValue(PEntityAttributes.FALL_DAMAGE_DIVISOR);
        return divisor == 0 ? 0 : MathHelper.ceil(originalReturnValue / divisor);
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
}