package com.prog.mixin;

import com.prog.itemOrBlock.PBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.block.SculkVeinBlock$SculkVeinGrowChecker")
public abstract class SculkVeinGrowCheckerMixin {

    @Redirect(
            method = "canGrow(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
                    ordinal = 0
            )
    )
    public boolean redirectIsOf(BlockState instance, Block block) {
        return instance.isOf(block) || instance.isOf(PBlocks.EVERGLOOM) || instance.isOf(PBlocks.PURE_EVERGLOOM_BLOCK);
    }
}