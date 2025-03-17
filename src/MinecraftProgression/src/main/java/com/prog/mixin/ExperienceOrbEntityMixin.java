package com.prog.mixin;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin {

    @Unique
    private final ExperienceOrbEntity self = (ExperienceOrbEntity) (Object) this;

    @Redirect(
            method = "onPlayerCollision",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/ExperienceOrbEntity;amount:I")
    )
    private int redirectAmount(ExperienceOrbEntity instance) {
        int total = self.amount * self.pickingCount;
        self.pickingCount = 1;
        return total;
    }

    // Uncomment to allow greater orb sizes
//    @Inject(method = "roundToOrbSize", at = @At("HEAD"), cancellable = true)
//    private static void expandRoundToOrbSize(int value, CallbackInfoReturnable<Integer> cir) {
//        var closest = closestPowerOfTwoMinusOne(value);
//        if (closest >= 5000) {
//            cir.setReturnValue(closest);
//            return;
//        }
//        if (value >= 2477) cir.setReturnValue(2477);
//        else if (value >= 1237) cir.setReturnValue(1237);
//        else if (value >= 617) cir.setReturnValue(617);
//        else if (value >= 307) cir.setReturnValue(307);
//        else if (value >= 149) cir.setReturnValue(149);
//        else if (value >= 73) cir.setReturnValue(73);
//        else if (value >= 37) cir.setReturnValue(37);
//        else if (value >= 17) cir.setReturnValue(17);
//        else if (value >= 7) cir.setReturnValue(7);
//        else cir.setReturnValue(value >= 3 ? 3 : 1);
//    }
//
    @Unique
    private static int closestPowerOfTwoMinusOne(int value) {
        int power = 1;
        while (power - 1 < value) {
            power <<= 1;
        }
        return power - 1;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void redirectWriteValue(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("Value", self.amount);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void redirectReadValue(NbtCompound nbt, CallbackInfo ci) {
        self.amount = nbt.getInt("Value");
    }
}
