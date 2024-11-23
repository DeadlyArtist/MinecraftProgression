package com.prog.mixin;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Redirect(method = "getArmPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean redirectIsOf(ItemStack instance, Item item) {
        return instance.getItem() instanceof CrossbowItem;
    }
}
