package com.prog.utils;

import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FishingUtils {

    public static class AllowedFluidTestResult {
        public boolean allowed;
        public boolean inWater;
        public boolean inLava;
        public boolean inVoid;
        public boolean voidBelow;
        public double originalPlayerY;
    }

    public static boolean isTreasure(Random random, float luck) {
        var offset = 10;
        if (offset + luck < 0) return false;

        var treasure = random.nextFloat() > offset / (offset + luck + 1);
        return treasure;
    }

    // For mixin use only
    public static AllowedFluidTestResult changeAllowedFluids(PlayerEntity owner, BlockPos pos, double originalPlayerY) {
        var world = owner.world;
        var result = new AllowedFluidTestResult();
        result.originalPlayerY = originalPlayerY;

        var fluidState = world.getFluidState(pos);
        var allowed = result.inWater = fluidState.isIn(FluidTags.WATER);
        if (!allowed && owner.getAttributeValue(PEntityAttributes.LAVA_FISHING) == 1)
            allowed = result.inLava = fluidState.isIn(FluidTags.LAVA);
        if (!allowed && owner.getAttributeValue(PEntityAttributes.VOID_FISHING) == 1) {
            var voidBelow = result.voidBelow = isVoidBelow(world, pos);
            var inVoid = voidBelow && pos.getY() < originalPlayerY - 2;
            allowed = result.inVoid = inVoid;
        }

        result.allowed = allowed;
        return result;
    }

    public static AllowedFluidTestResult changeAllowedFluids(FishingBobberEntity bobber) {
        return changeAllowedFluids(bobber.getPlayerOwner(), bobber.getBlockPos(), PComponents.PROJECTILE.get(bobber).originalPlayerY);
    }

    public static AllowedFluidTestResult changeAllowedFluids(FishingBobberEntity bobber, BlockPos pos) {
        return changeAllowedFluids(bobber.getPlayerOwner(), pos, PComponents.PROJECTILE.get(bobber).originalPlayerY);
    }

    public static boolean isVoidBelow(World world, BlockPos pos) {
        var voidBelow = true;
        BlockPos belowPos = pos.up();
        while (belowPos.getY() > world.getBottomY()) {
            belowPos = belowPos.down();
            if (!world.isAir(belowPos)) {
                voidBelow = false;
                break;
            }
        }
        return voidBelow;
    }

    // https://deadlyartist.github.io/aidevsuite/#local/live_calculator?mode=run
    // function logX(x, y) {
    //    return Math.log(y) / Math.log(x);
    //}
    //
    //var treasureQuality = 80
    //var logBase = 1.45;
    //var baseLogValue = Math.floor(logX(logBase, treasureQuality));
    //var desiredLevel = baseLogValue - 4;
    //var probabilityForHigherLevel = 0;
    //var qualityRequiredForCurrentLevel = 0;
    //var qualityRequiredForNextLevel = 0;
    //if (desiredLevel < 1) {
    //    desiredLevel = 1;
    //} else {
    //    qualityRequiredForCurrentLevel = Math.ceil(Math.pow(logBase, baseLogValue));
    //    qualityRequiredForNextLevel = Math.ceil(Math.pow(logBase, baseLogValue + 1));
    //    probabilityForHigherLevel = (treasureQuality - qualityRequiredForCurrentLevel) / (qualityRequiredForNextLevel - qualityRequiredForCurrentLevel);
    //}
    //
    //[desiredLevel, qualityRequiredForCurrentLevel, qualityRequiredForNextLevel, probabilityForHigherLevel].join("\t")
    public static ItemStack generateTreasure(Random random, double treasureQuality) {
        var logBase = 1.45;
        var baseLogValue = (int) MathUtils.logX(logBase, treasureQuality);
        var desiredLevel = baseLogValue - 4;
        var probabilityForHigherLevel = treasureQuality <= RangedUtils.BASE_TREASURE_QUALITY ? 0 : 0.1;
        if (desiredLevel < 1) {
            desiredLevel = 1;
        } else {
            var qualityRequiredForCurrentLevel = Math.ceil(Math.pow(logBase, baseLogValue));
            var qualityRequiredForNextLevel = Math.ceil(Math.pow(logBase, baseLogValue + 1));
            probabilityForHigherLevel = (treasureQuality - qualityRequiredForCurrentLevel) / (qualityRequiredForNextLevel - qualityRequiredForCurrentLevel);
        }
        if (random.nextDouble() < probabilityForHigherLevel) desiredLevel++;

        var reducedLevelProbability = 0.7;
        if (random.nextDouble() < reducedLevelProbability) desiredLevel = random.nextBetween(1, desiredLevel);

        var incrementProb = 0.05;
        desiredLevel = RandomUtils.randomIncrement(random, desiredLevel, incrementProb);

        var enchantment = EnchantmentUtils.getRandomEnchantmentLevelEntry(random, desiredLevel, true, EnchantmentUtils.BAD_ENCHANTMENTS);
        return EnchantedBookItem.forEnchantment(enchantment);
    }
}
