package com.prog.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;

import java.util.*;

public class BookUtils {
    public static String getInitialText(ItemStack book) {
        if (!book.hasNbt()) return "";
        var nbt = book.getNbt();

        if (!nbt.contains("pages", NbtElement.LIST_TYPE)) {
            return "";
        }

        var pages = nbt.getList("pages", NbtElement.STRING_TYPE);
        if (pages.isEmpty()) return "";

        // Return first page
        var string = pages.getString(0);
        if (book.isOf(Items.WRITTEN_BOOK)) {
            // Parse JSON text component and convert to plain string
            try {
                var parsed = Text.Serializer.fromJson(string);
                if (parsed != null) {
                    return parsed.getString();
                }
            } catch (Exception e) { }
        }
        return string;
    }

    public static Optional<Integer> parseIndex(String text, int maxIndex) {
        try {
            if (text.startsWith("^")) {
                // Inverse index handling
                String number = text.substring(1).replaceAll("[^0-9]", "");
                if (number.isEmpty()) return Optional.empty();

                int inverse = Integer.parseInt(number);
                int index = maxIndex - (inverse - 1);

                if (index < 0 || index > maxIndex) return Optional.empty();
                return Optional.of(index);
            } else {
                // Normal index from 1-based user input
                String sanitized = text.replaceAll("[^0-9]", "");
                if (sanitized.isEmpty()) return Optional.empty();

                int parsed = Integer.parseInt(sanitized) - 1; // Convert to zero-based index
                if (parsed < 0 || parsed > maxIndex) return Optional.empty();
                return Optional.of(parsed);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static List<Integer> parseIndices(String text, int maxIndex) {
        List<Integer> indices = new ArrayList<>();
        String[] parts = text.split(",");
        String first = parts[0].trim();

        // Wildcard mode with exclusions
        if (first.equals("*")) {
            // Fill HashSet with excluded indices
            Set<Integer> excluded = new HashSet<>();
            for (int i = 1; i < parts.length; i++) {
                var mayExclude = parseIndex(parts[i].trim(), maxIndex);
                if (mayExclude.isEmpty()) continue;
                var exclude = mayExclude.get();
                excluded.add(exclude);
            }

            // Add all allowed indices except exclusions
            for (int i = 0; i <= maxIndex; i++) {
                if (!excluded.contains(i)) {
                    indices.add(i);
                }
            }

        } else {
            // Manual index selection
            Set<Integer> added = new HashSet<>();

            for (String part : parts) {
                var mayIndex = parseIndex(part.trim(), maxIndex);
                if (mayIndex.isEmpty()) continue;
                var index = mayIndex.get();
                if (added.add(index)) indices.add(index);
            }
        }
        if (indices.isEmpty()) indices.add(maxIndex);

        return indices;
    }
}
