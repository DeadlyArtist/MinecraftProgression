package com.prog.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Redirect(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return instance.getItem() instanceof FishingRodItem;
    }

    @Redirect(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    public boolean redirectIsOf2(ItemStack instance, Item item) {
        return instance.getItem() instanceof FishingRodItem;
    }
}
