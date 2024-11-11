package com.prog.mixin;

import com.prog.entity.PComponents;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConsumeItemCriterion.class)
public class ConsumeItemCriterionMixin {
    @Inject(method = "trigger", at = @At("HEAD"))
    private void injectTrigger(ServerPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        PComponents.LIVING_ENTITY.get(player).eat(stack.getItem());
    }
}
