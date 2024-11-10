package com.prog.mixin.compat.universalenchants;

import fuzs.universalenchants.world.item.enchantment.data.AdditionalEnchantmentDataProvider;
import fuzs.universalenchants.world.item.enchantment.serialize.entry.DataEntry;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(AdditionalEnchantmentDataProvider.class)
public interface AdditionalEnchantmentDataProviderAccessor {
    @Invoker("applyIncompatibilityToBoth")
    static void applyIncompatibilityToBoth(Map<Enchantment, DataEntry.Builder> builders,
                                           Enchantment enchantment,
                                           Enchantment other,
                                           boolean add) {
        throw new UnsupportedOperationException();
    }
}
