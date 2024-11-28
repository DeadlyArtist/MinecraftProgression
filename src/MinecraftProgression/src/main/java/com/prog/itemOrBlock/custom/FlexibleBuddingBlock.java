package com.prog.itemOrBlock.custom;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.util.function.Supplier;

public class FlexibleBuddingBlock extends AmethystBlock {

    public int GROW_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();
    public Supplier<Block> stage1;
    public Supplier<Block> stage2;
    public Supplier<Block> stage3;
    public Supplier<Block> stage4;

    public FlexibleBuddingBlock(Supplier<Block> stage1, Supplier<Block> stage2, Supplier<Block> stage3, Supplier<Block> stage4, Settings settings) {
        super(settings);
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
        this.stage4 = stage4;
    }


    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(GROW_CHANCE) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState)) {
                block = stage1.get();
            } else if (blockState.isOf(stage1.get()) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = stage2.get();
            } else if (blockState.isOf(stage2.get()) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = stage3.get();
            } else if (blockState.isOf(stage3.get()) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = stage4.get();
            }

            if (block != null) {
                BlockState blockState2 = block.getDefaultState()
                        .with(AmethystClusterBlock.FACING, direction)
                        .with(AmethystClusterBlock.WATERLOGGED, Boolean.valueOf(blockState.getFluidState().getFluid() == Fluids.WATER));
                world.setBlockState(blockPos, blockState2);
            }
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }
}
