package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.handler.KeyBindingsHandler;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.lib.ModTooltips;
import com.blakebr0.ironjetpacks.network.NetworkHandler;
import com.blakebr0.ironjetpacks.network.message.ToggleEngineMessage;
import com.blakebr0.ironjetpacks.network.message.ToggleHoverMessage;
import com.prog.utils.JetpackUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyBindingsHandler.class, remap = false)
public class KeyBindingsHandlerMixin {
    @Inject(method = "handleInputs", at = @At("HEAD"), cancellable = true)
    private static void injectHandleInputs(MinecraftClient client, CallbackInfo ci) {
        PlayerEntity player = client.player;
        if (player != null) {
            ItemStack stack = JetpackUtils.getJetpackItemStack(player);
            if (stack != null) {
                Item item = stack.getItem();
                boolean on;
                MutableText state;
                // Access keyEngine via the accessor
                while (KeyBindingsHandlerAccessor.getKeyEngine().wasPressed()) {
                    NetworkHandler.sendToServer(new ToggleEngineMessage());
                    on = !JetpackUtils.isEngineOn(stack);
                    state = on ? ModTooltips.ON.color(Formatting.GREEN) : ModTooltips.OFF.color(Formatting.RED);
                    player.sendMessage(ModTooltips.TOGGLE_ENGINE.args(state), true);
                }

                // Access keyHover via the accessor
                while (KeyBindingsHandlerAccessor.getKeyHover().wasPressed()) {
                    NetworkHandler.sendToServer(new ToggleHoverMessage());
                    on = !JetpackUtils.isHovering(stack);
                    state = on ? ModTooltips.ON.color(Formatting.GREEN) : ModTooltips.OFF.color(Formatting.RED);
                    player.sendMessage(ModTooltips.TOGGLE_HOVER.args(state), true);
                }
            }
        }

        ci.cancel();
    }
}
