package com.prog.mixin.compat.ringofattraction;

import com.kwpugh.ring_of_attraction.util.MagnetUtil;
import com.prog.entity.PComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagnetUtil.class)
public class MagnetUtilMixin {

    @Inject(
            method = "doMagnet",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void injectDoMagnet(World world, PlayerEntity playerIn, ItemStack stack, CallbackInfo ci) {
        if (PComponents.PLAYER.get(playerIn).magnetDisabled) ci.cancel();
    }
}
