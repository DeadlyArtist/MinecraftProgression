package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.itemOrBlock.PBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.SculkPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SculkPatchFeature.class)
public class SculkPatchFeatureMixin {
    @Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", ordinal = 0))
    public boolean redirectGenerateSculk(StructureWorldAccess instance, BlockPos blockPos, BlockState blockState, int i, @Local Random random) {
        if (random.nextDouble() < 0.66) instance.setBlockState(blockPos, Blocks.SCULK_CATALYST.getDefaultState(), Block.NOTIFY_ALL);
        else instance.setBlockState(blockPos, PBlocks.EVERGLOOM.getDefaultState(), Block.NOTIFY_ALL);
        return true;
    }
}
