package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import com.prog.client.entity.PEntityModelLayers;
import com.prog.event.RendererEvents;
import com.prog.itemOrBlock.custom.TieredShieldItem;
import com.prog.itemOrBlock.custom.TieredTridentItem;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

    @Final
    @Shadow
    private EntityModelLoader entityModelLoader;

    @Inject(method = "reload", at = @At("HEAD"))
    public void injectReload(ResourceManager manager, CallbackInfo ci) {
        RendererEvents.RELOAD_MODELS.invoker().onReloadModels(entityModelLoader);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean redirectRenderShield(ItemStack instance, Item shield, @Local ItemStack stack, @Local ModelTransformation.Mode mode, @Local MatrixStack matrices, @Local VertexConsumerProvider vertexConsumers, @Local(ordinal = 0) int light, @Local(ordinal = 1) int overlay) {
        var item = stack.getItem();
        if (item == shield) return true;
        if (true) return false;
        else if (!(item instanceof TieredShieldItem tiered)) return false;

        var id = ItemUtils.getId(item);
        var model = (ShieldEntityModel)PEntityModelLayers.getModel(item);

        boolean bl = BlockItem.getBlockEntityNbt(stack) != null;
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);

        SpriteIdentifier spriteIdentifier = bl ? new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/" + id.getPath() + "_base")) : new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(id.getNamespace(), "entity/" + id.getPath() + "_base_nopattern"));
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite()
                .getTextureSpecificVertexConsumer(
                        ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, model.getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint())
                );
        model.getHandle().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        if (bl) {
            List<Pair<RegistryEntry<BannerPattern>, DyeColor>> list = BannerBlockEntity.getPatternsFromNbt(
                    ShieldItem.getColor(stack), BannerBlockEntity.getPatternListNbt(stack)
            );
            BannerBlockEntityRenderer.renderCanvas(
                    matrices, vertexConsumers, light, overlay, model.getPlate(), spriteIdentifier, false, list, stack.hasGlint()
            );
        } else {
            model.getPlate().render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrices.pop();

        return false;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    public boolean redirectRenderTrident(ItemStack instance, Item trident, @Local ItemStack stack, @Local ModelTransformation.Mode mode, @Local MatrixStack matrices, @Local VertexConsumerProvider vertexConsumers, @Local(ordinal = 0) int light, @Local(ordinal = 1) int overlay) {
        var item = stack.getItem();
        if (item == trident) return true;
        if (true) return false;
        else if (!(item instanceof TieredTridentItem tiered)) return false;

        var id = ItemUtils.getId(item);
        var model = (TridentEntityModel) PEntityModelLayers.getModel(item);

        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexConsumer2 = ItemRenderer.getDirectItemGlintConsumer(
                vertexConsumers, model.getLayer(new Identifier(id.getNamespace(), "textures/entity/" + id.getPath() + ".png")), false, stack.hasGlint()
        );
        model.render(matrices, vertexConsumer2, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

        return false;
    }
}
