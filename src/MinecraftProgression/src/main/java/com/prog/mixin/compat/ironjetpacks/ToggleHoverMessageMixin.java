package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.network.message.ToggleHoverMessage;
import com.prog.utils.JetpackUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToggleHoverMessage.class)
public class ToggleHoverMessageMixin {

    @Inject(method = "onMessage", at = @At("HEAD"), cancellable = true)
    private static void injectOnMessage(ToggleHoverMessage message, MinecraftServer server, ServerPlayerEntity player, CallbackInfo ci) {
        server.execute(() -> {
            if (player != null) {
                ItemStack stack = JetpackUtils.getJetpackItemStack(player);
                if (stack != null) {
                    JetpackUtils.toggleHover(stack);
                }
            }
        });

        ci.cancel();
    }
}
