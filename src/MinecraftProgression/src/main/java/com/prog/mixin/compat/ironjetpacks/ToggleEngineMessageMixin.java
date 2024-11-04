package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.network.message.ToggleEngineMessage;
import com.prog.utils.JetpackUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToggleEngineMessage.class)
public class ToggleEngineMessageMixin {

    @Inject(method = "onMessage", at = @At("HEAD"), cancellable = true)
    private static void injectOnMessage(ToggleEngineMessage message, MinecraftServer server, ServerPlayerEntity player, CallbackInfo ci) {
        server.execute(() -> {
            if (player != null) {
                ItemStack stack = JetpackUtils.getJetpackItemStack(player);
                if (stack != null) {
                    JetpackUtils.toggleEngine(stack);
                }
            }
        });

        ci.cancel();
    }
}
