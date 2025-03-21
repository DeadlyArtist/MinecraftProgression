package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.prog.client.utils.ItemModelRegistry;
import com.prog.client.utils.RenderUtils;
import com.prog.utils.ItemUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0))
    public boolean redirectIsEmpty(ItemStack instance, @Local ModelTransformation.Mode renderMode) {
        boolean bl = renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;
        if (!bl && instance.getItem() instanceof TridentItem && instance.hasNbt() && instance.getNbt().contains("thrown")) return true;
        return instance.isEmpty();
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        //if (true) return instance.isOf(item);
        return instance.getItem() instanceof TridentItem;
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 2))
    public boolean redirectIsOf2(ItemStack instance, Item item) {
        if (true) return instance.isOf(item);
        return instance.getItem() instanceof TridentItem;
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;", ordinal = 0))
    public BakedModel redirectRenderTrident(BakedModelManager instance, ModelIdentifier id, @Local ItemStack stack) {
        var model = instance.getModel(new ModelIdentifier(ItemUtils.getId(stack.getItem()) + "#inventory"));
        return model;
    }

    @Redirect(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;", ordinal = 0))
    public BakedModel redirectRenderTrident2(BakedModelManager instance, ModelIdentifier id, @Local ItemStack stack) {
        return instance.getModel(new ModelIdentifier(ItemUtils.getId(stack.getItem()) + "_in_hand#inventory"));
    }

    @Inject(method = "renderGuiItemModel", at = @At("TAIL"))
    private void injectGreyOverlay(ItemStack stack, int x, int y, BakedModel model, CallbackInfo ci) {
        if (stack.getItem() instanceof TridentItem && stack.hasNbt() && stack.getNbt().contains("thrown")) {
            RenderUtils.renderGreyOverlay(x, y);
        }
    }


    // FROM: https://github.com/fzzyhmstrs/fc/tree/1.19.2-New

    @Shadow
    @Final
    private ItemModels models;
    @Shadow
    public abstract void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);
    @Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At(value = "INVOKE", target = "net/minecraft/client/render/item/ItemRenderer.getModel (Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"))
    private void fzzy_core_renderCustomItemModel(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
        Item item = stack.getItem();
        if (ItemModelRegistry.itemHasCustomModel(item)) {
            ModelIdentifier modelId = ItemModelRegistry.getModel(item, renderMode);
            BakedModel bakedModel = this.models.getModelManager().getModel(modelId);
            ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld) world : null;
            BakedModel bakedModel2 = bakedModel.getOverrides().apply(bakedModel, stack, clientWorld, entity, 0);
            bakedModel2 = bakedModel2 == null ? this.models.getModelManager().getMissingModel() : bakedModel2;
            renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, bakedModel2);
            matrices.scale(0.0f, 0.0f, 0.0f);
        }
    }
}
