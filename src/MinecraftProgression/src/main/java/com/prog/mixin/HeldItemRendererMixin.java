package com.prog.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Redirect(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean redirectIsBowType(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof BowItem;
    }

    @Redirect(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private static boolean redirectIsBowTypeOffHand(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof BowItem;
    }

    @Redirect(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 2))
    private static boolean redirectIsCrossbowType(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof CrossbowItem;
    }

    @Redirect(method = "getHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 3))
    private static boolean redirectIsCrossbowTypeOffHand(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof CrossbowItem;
    }

    @Redirect(method = "getUsingItemHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean redirectUsingBowType(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof BowItem;
    }

    @Redirect(method = "getUsingItemHandRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private static boolean redirectUsingCrossbowType(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof CrossbowItem;
    }
}
