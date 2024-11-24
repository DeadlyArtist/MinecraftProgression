package com.prog.utils;

import net.minecraft.util.math.random.Random;

public class RandomUtils {

    /**
     * Applies a random offset to a baseValue within a ±maxOffset percentage.
     *
     * @param baseValue the original value to apply the random offset to.
     * @param maxOffset the maximum percentage offset (e.g., 0.2 for 20%).
     * @return the base value adjusted by a random factor within ±maxOffset percentage.
     * If maxOffset is 0, the base value is returned unmodified.
     */
    public static double randomOffset(Random random, double baseValue, double maxOffset) {
        if (maxOffset == 0) return baseValue;

        // Generate a random factor between (1 - maxOffset) and (1 + maxOffset)
        double randomFactor = 1 - maxOffset + (2 * maxOffset * random.nextDouble());
        return baseValue * randomFactor;
    }

    /**
     * Generates a random double value between a specified minimum and maximum range.
     *
     * @param random the Random instance for generating random numbers.
     * @param min    the minimum value that can be returned (inclusive).
     * @param max    the maximum value that can be returned (exclusive).
     * @return a random double between min (inclusive) and max (exclusive).
     * If min is equal to max, returns min.
     * If max is less than min, returns NaN.
     */
    public static double randomDouble(Random random, double min, double max) {
        if (min == max) {
            return min;
        }
        if (max < min) {
            throw new IllegalArgumentException("Max must be greater than or equal to Min");
        }

        // Generate a random double within the range [min, max)
        return min + (random.nextDouble() * (max - min));
    }

    /**
     * Recursively increments the value based on the given probability.
     *
     * @param prob     The probability of incrementing the value (0.0 to 1.0).
     * @param startVal The starting value (default 0).
     * @param maxVal   The maximum value to reach (default Integer.MAX_VALUE).
     * @return The final recursively generated value.
     */
    public static int randomIncrement(Random random, int startVal, int maxVal, double prob) {
        if (startVal >= maxVal) {
            return startVal;
        }

        double randomChance;
        do {
            randomChance = random.nextDouble();
        } while (randomChance <= prob && startVal++ < maxVal);

        return startVal;
    }

    public static int randomIncrement(Random random, int startVal, double prob) {
        return randomIncrement(random, startVal, Integer.MAX_VALUE, prob);
    }
}
