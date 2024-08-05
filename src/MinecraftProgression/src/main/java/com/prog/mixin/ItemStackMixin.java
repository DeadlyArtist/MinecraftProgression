package com.prog.mixin;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("TAIL"))
    private void onConstructorHead(ItemConvertible item, int count, CallbackInfo ci) {
        // Injected code goes here
        System.out.println("ItemStack constructor called with item: " + item + ", count: " + count);
        // Add any additional logic you want to execute at the beginning of the constructor
    }
}
