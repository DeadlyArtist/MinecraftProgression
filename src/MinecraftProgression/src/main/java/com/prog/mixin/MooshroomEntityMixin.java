package com.prog.mixin;

import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MooshroomEntity.class)
public class MooshroomEntityMixin {
    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return instance.getItem() instanceof ShearsItem;
    }
}
