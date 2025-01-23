package com.prog.mixin;

import com.prog.event.EntityEvents;
import com.prog.utils.LOGGER;
import com.prog.utils.SilentUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Unique
    private final Entity self = (Entity)(Object)this;

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        EntityEvents.ENTITY_TICK.invoker().tick(self);
    }

    // Uncomment to disable all sounds if silent
//    @Inject(method = "isSilent", at = @At("HEAD"), cancellable = true)
//    protected void injectIsSilent(CallbackInfoReturnable<Boolean> cir) {
//        if (self instanceof LivingEntity entity && SilentUtils.isSilent(entity)) {
//            cir.setReturnValue(true);
//        }
//    }

    // Would need custom isTouchingWater and isSubmergedInWater variable management but for lava, in order to allow swimming in lava while immune to it.
//    @Redirect(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/TagKey;)Z"))
//    private boolean redirectUpdateSwimming(FluidState instance, TagKey<Fluid> tag) {
//        return instance.isIn(FluidTags.WATER) || (self.isFireImmune() && instance.isIn(FluidTags.LAVA));
//    }
}