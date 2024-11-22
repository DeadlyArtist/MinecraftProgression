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

        String[] parts = input.toLowerCase().split("[ _]");
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

    /**
     * Splits the input string using the provided delimiter and returns the last element.
     *
     * @param input     The input string to be split.
     * @param delimiter The delimiter to split by.
     * @return The last element after splitting the string, or null if the input is null, empty, or the delimiter is not found.
     */
    public static String getLastElement(String input, String delimiter) {
        if (input == null || input.isEmpty() || delimiter == null || delimiter.isEmpty()) {
            return null;
        }

        String[] parts = input.split(delimiter); // Split the string using the delimiter

        if (parts.length == 0) {
            return null;
        }

        return parts[parts.length - 1];
    }
}