package com.prog.mixin;

import com.prog.entity.PComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Inject(method = "finishUsing", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"))
    private void afterIncrementStat(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        PComponents.LIVING_ENTITY.get(user).eat(stack.getItem());
    }
}