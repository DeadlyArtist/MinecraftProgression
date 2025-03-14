package com.prog.mixin;

import com.prog.itemOrBlock.PItemTags;
import com.prog.utils.ItemUtils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(at = @At("HEAD"), method = "isFireImmune", cancellable = true)
    private void isFireImmune(CallbackInfoReturnable<Boolean> cir) {
        var self = (ItemEntity) (Object) this;
        var stack = self.getStack();
        if (ItemUtils.isFireproof(stack)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
