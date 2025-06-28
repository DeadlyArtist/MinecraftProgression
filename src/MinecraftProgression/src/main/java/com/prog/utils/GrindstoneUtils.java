package com.prog.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.*;
import java.util.stream.Collectors;

public class GrindstoneUtils {
    public static boolean canAdditionallyInsertIntoStack1(ItemStack stack) {
        return false;
    }

    public static boolean canAdditionallyInsertIntoStack2(ItemStack stack) {
        return isAllowedBook(stack);
    }

    public static boolean isAllowedBook(ItemStack stack) {
        return stack.isOf(Items.BOOK) || stack.isOf(Items.WRITABLE_BOOK) || stack.isOf(Items.WRITTEN_BOOK);
    }

    public static boolean isSpecialTransfer(ItemStack slot1, ItemStack slot2) {
        return slot1.hasEnchantments() && isAllowedBook(slot2);
    }

    public static List<Integer> getTargetEnchantmentIndices(Map<Enchantment, Integer> enchantments, ItemStack slot2) {
        int maxIndex = enchantments.size() - 1;

        if (slot2.isOf(Items.WRITABLE_BOOK) || slot2.isOf(Items.WRITTEN_BOOK)) {
            String pageText = BookUtils.getInitialText(slot2);
            return BookUtils.parseIndices(pageText, maxIndex)
                    .stream()
                    .distinct()
                    .filter(i -> i >= 0 && i <= maxIndex)
                    .sorted()
                    .collect(Collectors.toList());
        }

        return List.of(maxIndex);
    }

    public static ItemStack getOutput(ItemStack slot1, ItemStack slot2) {
        if (!isSpecialTransfer(slot1, slot2)) return ItemStack.EMPTY;

        var enchantments = EnchantmentHelper.get(slot1);
        var enchantList = new ArrayList<>(enchantments.entrySet());
        var targetIndices = getTargetEnchantmentIndices(enchantments, slot2);

        if (targetIndices.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> selected = new LinkedHashMap<>();

        for (int index : targetIndices) {
            if (index >= 0 && index < enchantList.size()) {
                var entry = enchantList.get(index);
                selected.put(entry.getKey(), entry.getValue());
            }
        }

        if (selected.isEmpty()) return ItemStack.EMPTY;

        EnchantmentHelper.set(selected, result);
        return result;
    }

    public static ItemStack getUpdatedSlot1(ItemStack slot1, ItemStack slot2) {
        if (!isSpecialTransfer(slot1, slot2)) return ItemStack.EMPTY;

        ItemStack updatedStack = slot1.copy();
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(updatedStack);
        List<Map.Entry<Enchantment, Integer>> enchantList = new ArrayList<>(enchantments.entrySet());

        List<Integer> indicesToRemove = getTargetEnchantmentIndices(enchantments, slot2);

        // Remove duplicates and sort descending to avoid index shift issues during removal
        indicesToRemove = indicesToRemove.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        for (int index : indicesToRemove) {
            if (index >= 0 && index < enchantList.size()) {
                Enchantment enchantToRemove = enchantList.get(index).getKey();
                enchantments.remove(enchantToRemove);
            }
        }

        EnchantmentHelper.set(enchantments, updatedStack);
        return updatedStack;
    }

    public static ItemStack getUpdatedSlot2(ItemStack slot1, ItemStack slot2) {
        if (!isSpecialTransfer(slot1, slot2)) return ItemStack.EMPTY;

        // Reduce count by 1
        var count = slot2.getCount();
        if (count <= 1) return ItemStack.EMPTY;
        slot2.setCount(count - 1);
        return slot2;
    }
}
