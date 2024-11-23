package com.prog.client.utils;

import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.client.model.Model;

import java.util.function.Supplier;

// FROM: https://github.com/fzzyhmstrs/tns/tree/1.20.1
public class BuiltinEntityItemEntityRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final Identifier texture;

    // Lazily loaded model loader
    private final Supplier<ItemModelRegistry.CustomItemEntityModelLoader> modelLoaderProvider;

    public BuiltinEntityItemEntityRenderer(Item item, Identifier texture) {
        this.texture = texture;
        this.modelLoaderProvider = Suppliers.memoize(() -> ItemModelRegistry.getEntityModelLoader(item));
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Get the render model
        Model renderModel = modelLoaderProvider.get().getModel();

        // Push the transformation matrix
        matrices.push();

        // Scale the model (mirror vertically)
        matrices.scale(1.0f, -1.0f, -1.0f);

        // Get a vertex consumer (with or without glint depending on the stack)
        var block = ItemRenderer.getDirectItemGlintConsumer(
                vertexConsumers,
                renderModel.getLayer(texture),
                false,
                stack.hasGlint()
        );

        // Render the model
        renderModel.render(matrices, block, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        // Pop the transformation matrix
        matrices.pop();
    }
}
