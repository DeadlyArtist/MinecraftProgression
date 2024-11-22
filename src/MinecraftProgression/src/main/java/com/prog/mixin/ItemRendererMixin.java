package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.ItemUtils;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;", ordinal = 0))
    public BakedModel redirectRenderTrident(BakedModelManager instance, ModelIdentifier id, @Local ItemStack stack) {

        return instance.getModel(new ModelIdentifier(ItemUtils.getId(stack.getItem()) + "#inventory"));
    }

    @Redirect(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;", ordinal = 0))
    public BakedModel redirectRenderTrident2(BakedModelManager instance, ModelIdentifier id, @Local ItemStack stack) {

        return instance.getModel(new ModelIdentifier(ItemUtils.getId(stack.getItem()) + "_in_hand#inventory"));
    }
}
