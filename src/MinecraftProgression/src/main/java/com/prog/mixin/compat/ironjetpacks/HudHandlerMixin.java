package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.client.util.HudHelper;
import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.handler.HudHandler;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.itemOrBlock.PJetpacks;
import com.prog.utils.JetpackUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(HudHandler.class)
public class HudHandlerMixin {
    @Unique
    private static final Identifier HUD_TEXTURE = new Identifier("iron-jetpacks", "textures/gui/hud.png");

    @Inject(method = "onRenderGameOverlay", at = @At("HEAD"), cancellable = true)
    private static void injectOnRenderGameOverlay(MatrixStack matrices, float delta, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null && ModConfigs.getClient().hud.enableHud && (ModConfigs.getClient().hud.showHudOverChat || !ModConfigs.getClient().hud.showHudOverChat && !(mc.currentScreen instanceof ChatScreen)) && !mc.options.hudHidden && !mc.options.debugEnabled) {
            ItemStack chest = JetpackUtils.getJetpackItemStack(mc.player);
            if (chest != null) {
                JetpackItem jetpack = JetpackUtils.tryGetJetpackItem(mc.player);
                var hasEnergy = JetpackUtils.hasEnergy(chest.getItem());
                HudHelper.HudPos pos = HudHelper.getHudPos();
                if (pos != null) {
                    int xPos = (int) ((double) pos.x / 0.33) - 18;
                    int yPos = (int) ((double) pos.y / 0.33) - 78;
                    if (hasEnergy) RenderSystem.setShaderTexture(0, HUD_TEXTURE);
                    matrices.push();
                    matrices.scale(0.33F, 0.33F, 1.0F);
                    if (hasEnergy) DrawableHelper.drawTexture(matrices, xPos, yPos, 0, 0.0F, 0.0F, 28, 156, 256, 256);
                    if (hasEnergy) {
                        int i2 = HudHelper.getEnergyBarScaled(jetpack, chest);
                        DrawableHelper.drawTexture(matrices, xPos, 166 - i2 + yPos - 10, 0, 28.0F, (float) (156 - i2), 28, i2, 256, 256);
                    }
                    matrices.pop();
                    Formatting var10000 = Formatting.GRAY;
                    String fuel = "" + var10000 + (!hasEnergy ? 0 : HudHelper.getFuel(jetpack, chest));
                    String engine = "" + var10000 + "E: " + HudHelper.getOn(JetpackUtils.isEngineOn(chest));
                    String hover = "" + var10000 + "H: " + HudHelper.getOn(JetpackUtils.isHovering(chest));
                    if (pos.side == 1) {
                        var offset = hasEnergy ? 8 : 2;
                        if (hasEnergy) mc.textRenderer.drawWithShadow(matrices, fuel, (float) (pos.x - offset - mc.textRenderer.getWidth(fuel)), (float) (pos.y - 21), 16383998);
                        mc.textRenderer.drawWithShadow(matrices, engine, (float) (pos.x - offset - mc.textRenderer.getWidth(engine)), (float) (pos.y + 4), 16383998);
                        mc.textRenderer.drawWithShadow(matrices, hover, (float) (pos.x - offset - mc.textRenderer.getWidth(hover)), (float) (pos.y + 14), 16383998);
                    } else {
                        var offset = hasEnergy ? 6 : 0;
                        if(hasEnergy) mc.textRenderer.drawWithShadow(matrices, fuel, (float) (pos.x + offset), (float) (pos.y - 21), 16383998);
                        mc.textRenderer.drawWithShadow(matrices, engine, (float) (pos.x + offset), (float) (pos.y + 4), 16383998);
                        mc.textRenderer.drawWithShadow(matrices, hover, (float) (pos.x + offset), (float) (pos.y + 14), 16383998);
                    }

                    RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
                }
            }
        }

        ci.cancel();
    }
}

