package com.prog.utils;

import net.minecraft.util.math.random.Random;

public class StringUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String random(int length) {
        Random random = Random.create();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static String random() {
        return random(20);
    }

    public static String toNormalCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Split the input by underscores and then capitalize each part
        String[] parts = input.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1))
                        .append(" ");
            }
        }

        return result.toString().trim();
    }
}