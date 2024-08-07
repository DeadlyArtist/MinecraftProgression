package com.prog.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import com.prog.Prog;
import com.prog.screen.FlexibleCraftingScreenHandler;
import com.prog.utils.ScreenUtils;
import com.prog.utils.SlotUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class FlexibleCraftingScreen extends HandledScreen<FlexibleCraftingScreenHandler> {
    private final Identifier texture;
    public FlexibleCraftingScreen(FlexibleCraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        texture = ScreenUtils.getScreenBackgroundTexture(handler);
        this.backgroundHeight = 112 + handler.height * SlotUtils.SIZE;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
        this.titleX = 25;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double) left
                || mouseY < (double) top
                || mouseX >= (double) (left + this.backgroundWidth)
                || mouseY >= (double) (top + this.backgroundHeight);
        return bl;
    }
}
