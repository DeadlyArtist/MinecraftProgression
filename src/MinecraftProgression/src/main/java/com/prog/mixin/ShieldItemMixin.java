package com.prog.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShieldItem.class)
public class ShieldItemMixin {

    @Redirect(method = "getTranslationKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BlockItem;getBlockEntityNbt(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/nbt/NbtCompound;"))
    public NbtCompound disableColorPrefix(ItemStack stack) {
        return null;
    }
}
