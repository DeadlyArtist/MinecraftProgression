package com.prog.client.utils;

import com.mojang.datafixers.util.Pair;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
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
        LOGGER.info(stack.getTranslationKey());
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
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/" + id.getPath() + "_base"));
    }

    public static SpriteIdentifier getShieldBaseNoPatternSpriteIdentifier(Item item) {
        var id = ItemUtils.getId(item);
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/" + id.getPath() + "_base_nopattern"));
    }
}
