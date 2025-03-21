package com.prog.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;

import java.util.List;

import static net.minecraft.item.ShieldItem.getColor;

public class RenderUtils {
    public static void renderBanner(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ShieldEntityModel model, SpriteIdentifier base, SpriteIdentifier base_nopattern) {
        boolean bl = stack.getSubNbt("BlockEntityTag") != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteIdentifier spriteIdentifier = bl ? base : base_nopattern;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, model.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
        model.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        if (bl) {
            List<Pair<RegistryEntry<BannerPattern>, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(getColor(stack), BannerBlockEntity.getPatternListNbt(stack));
            BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, model.getPlate(), spriteIdentifier, false, list, stack.hasGlint());
        } else {
            model.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrices.pop();
    }

    public static SpriteIdentifier getShieldBaseSpriteIdentifier(Item item) {
        var id = ItemUtils.getId(item);
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/" + id.getPath() + "_base_nopattern")); // should be _base, but causes black sprite for some reason
    }

    public static SpriteIdentifier getShieldBaseNoPatternSpriteIdentifier(Item item) {
        var id = ItemUtils.getId(item);
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/" + id.getPath() + "_base_nopattern"));
    }


    public static void renderGreyOverlay(int x, int y) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        // Setting overlay color (semi-transparent black/grey)
        float alpha = 0.6f; // Adjust for desired transparency
        int grey = 80; // Adjust for how dark the overlay should be (0 = black, 255 = white)

        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        buffer.vertex(x, y + 16, 200.0).color(grey, grey, grey, (int) (alpha * 255)).next();
        buffer.vertex(x + 16, y + 16, 200.0).color(grey, grey, grey, (int) (alpha * 255)).next();
        buffer.vertex(x + 16, y, 200.0).color(grey, grey, grey, (int) (alpha * 255)).next();
        buffer.vertex(x, y, 200.0).color(grey, grey, grey, (int) (alpha * 255)).next();

        BufferRenderer.drawWithShader(buffer.end());

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
}
