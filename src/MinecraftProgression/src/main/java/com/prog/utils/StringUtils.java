package com.prog.utils;

public class StringUtils {
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