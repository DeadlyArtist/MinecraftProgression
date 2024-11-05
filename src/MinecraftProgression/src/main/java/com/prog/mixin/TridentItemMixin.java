package com.prog.mixin;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin {
//    @Inject(method = "getMaxUseTime", at = @At("RETURN"), cancellable = true)
//    private void modifyBowMaxUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
//        var entity = stack.getHolder();
//        if (!(entity instanceof LivingEntity livingEntity)) return;
//        int originalValue = cir.getReturnValue();
//        var speed = livingEntity.getAttributeValue(PEntityAttributes.CHARGING_SPEED);
//        int modifiedValue = speed == 0 ? 0 : (int) (originalValue / speed);
//        cir.setReturnValue(modifiedValue);
//    }
}
