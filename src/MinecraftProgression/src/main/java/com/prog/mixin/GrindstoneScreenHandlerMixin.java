package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.GrindstoneUtils;
import com.prog.utils.LOGGER;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {
    @Unique
    private final GrindstoneScreenHandler self = (GrindstoneScreenHandler) (Object) this;

    @Final
    @Shadow
    private Inventory result;

    @Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$2")
    public static class Slot1ClassMixin {
        @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
        private void canInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            cir.setReturnValue(cir.getReturnValue() || GrindstoneUtils.canAdditionallyInsertIntoStack1(stack));
        }
    }

    @Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$3")
    public static class Slot2ClassMixin {
        @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
        private void canInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            cir.setReturnValue(cir.getReturnValue() || GrindstoneUtils.canAdditionallyInsertIntoStack2(stack));
        }
    }

    @Mixin(targets = "net/minecraft/screen/GrindstoneScreenHandler$4")
    public static class Slot3ClassMixin {
        @Shadow @Final
        GrindstoneScreenHandler field_16780;

        @Redirect(method = "method_17417(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"))
        private void spawnXP(ServerWorld world, Vec3d pos, int amount) {
            var slot1 = field_16780.getSlot(0).getStack();
            var slot2 = field_16780.getSlot(1).getStack();
            if (!GrindstoneUtils.isSpecialTransfer(slot1, slot2)) ExperienceOrbEntity.spawn(world, pos, amount);
        }

        @Redirect(method = "onTakeItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 0))
        private void setStack1(Inventory input, int i, ItemStack empty) {
            var slot1 = input.getStack(0);
            var slot2 = input.getStack(1);
            var newSlot1 = GrindstoneUtils.getUpdatedSlot1(slot1, slot2);
            var newSlot2 = GrindstoneUtils.getUpdatedSlot2(slot1, slot2);

            input.setStack(0, newSlot1);
            input.setStack(1, newSlot2);
        }

        @Redirect(method = "onTakeItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 1))
        private void setStack2(Inventory input, int i, ItemStack empty) {
            // do nothing
        }
    }

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void onUpdateResults(CallbackInfo ci) {
        var slot1 = self.getSlot(0).getStack();
        var slot2 = self.getSlot(1).getStack();
        if (!GrindstoneUtils.isSpecialTransfer(slot1, slot2)) return;
        var output = GrindstoneUtils.getOutput(slot1, slot2);
        result.setStack(0, output);

        ci.cancel();
    }
}
