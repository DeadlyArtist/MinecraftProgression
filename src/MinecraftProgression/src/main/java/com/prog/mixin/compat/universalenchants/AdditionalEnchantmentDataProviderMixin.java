package com.prog.mixin.compat.universalenchants;

import com.prog.PSettings;
import fuzs.universalenchants.world.item.enchantment.data.AdditionalEnchantmentDataProvider;
import fuzs.universalenchants.world.item.enchantment.serialize.entry.DataEntry;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(value = AdditionalEnchantmentDataProvider.class, remap = false)
public class AdditionalEnchantmentDataProviderMixin {

    // Inject after the 'Registry.ENCHANTMENT.iterator()' iterable line.
    @Inject(
            method = "setupAdditionalCompatibility",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void injectSetupAdditionalCompatibilityMixin(Map<Enchantment, DataEntry.Builder> builders, CallbackInfo ci) {
        if (!PSettings.COMBINE_MORE_ENCHANTS) return;

        AdditionalEnchantmentDataProviderAccessor.applyIncompatibilityToBoth(builders, Enchantments.INFINITY, Enchantments.MENDING, false);
        AdditionalEnchantmentDataProviderAccessor.applyIncompatibilityToBoth(builders, Enchantments.MULTISHOT, Enchantments.PIERCING, false);

        List<DamageEnchantment> damageEnchantments = new ArrayList<>();
        List<ProtectionEnchantment> protectionEnchantments = new ArrayList<>();

        // Collect all DamageEnchantment and ProtectionEnchantment instances
        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if (enchantment instanceof DamageEnchantment) {
                damageEnchantments.add((DamageEnchantment) enchantment);
            } else if (enchantment instanceof ProtectionEnchantment) {
                protectionEnchantments.add((ProtectionEnchantment) enchantment);
            }
        }

        // Remove incompatibility between all DamageEnchantment combinations
        for (int i = 0; i < damageEnchantments.size(); i++) {
            for (int j = i + 1; j < damageEnchantments.size(); j++) {
                AdditionalEnchantmentDataProviderAccessor.applyIncompatibilityToBoth(builders, damageEnchantments.get(i), damageEnchantments.get(j), false);
            }
        }

        // Remove incompatibility between all ProtectionEnchantment combinations
        for (int i = 0; i < protectionEnchantments.size(); i++) {
            for (int j = i + 1; j < protectionEnchantments.size(); j++) {
                AdditionalEnchantmentDataProviderAccessor.applyIncompatibilityToBoth(builders, protectionEnchantments.get(i), protectionEnchantments.get(j), false);
            }
        }

        ci.cancel();
    }
}
