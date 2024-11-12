package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.entity.PComponents;
import com.prog.utils.DragonUtils;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.DragonFireballEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.DragonFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(DragonFireballEntityRenderer.class)
public class DragonFireballEntityRendererMixin {
    @Redirect(method = "render(Lnet/minecraft/entity/projectile/DragonFireballEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"))
    private void redirectRender(MatrixStack instance, float x, float y, float z, @Local DragonFireballEntity entity) {
        var value = (float) ((2.5 + DragonUtils.getPhase(entity) / 3D) * PComponents.DRAGON_FIREBALL.get(entity).sizeMultiplier);
        instance.scale(value, value, value);
    }
}
