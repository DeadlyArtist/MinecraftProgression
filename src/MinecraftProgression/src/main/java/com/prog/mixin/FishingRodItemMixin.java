package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.entity.PComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {

    @Redirect(method = "use", at = @At(value = "NEW", target = "(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;II)Lnet/minecraft/entity/projectile/FishingBobberEntity;"))
    public FishingBobberEntity onUse(PlayerEntity thrower, World world, int luckOfTheSeaLevel, int lureLevel, @Local ItemStack stack) {
        var bobber = new FishingBobberEntity(thrower, world, luckOfTheSeaLevel, lureLevel);
        var pComponent = PComponents.PROJECTILE.get(bobber);
        pComponent.setSourceStack(stack);
        pComponent.originalPlayerY = thrower.getY();
        return bobber;
    }
}
