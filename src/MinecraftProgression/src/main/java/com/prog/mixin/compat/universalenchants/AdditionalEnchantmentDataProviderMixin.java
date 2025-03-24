package com.prog.mixin.compat.universalenchants;

import com.google.gson.JsonArray;
import com.llamalad7.mixinextras.sugar.Local;
import com.prog.PSettings;
import com.prog.utils.LOGGER;
import fuzs.universalenchants.world.item.enchantment.data.AdditionalEnchantmentDataProvider;
import fuzs.universalenchants.world.item.enchantment.serialize.entry.DataEntry;
import fuzs.universalenchants.world.item.enchantment.serialize.entry.TypeEntry;
import net.minecraft.enchantment.*;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(value = AdditionalEnchantmentDataProvider.class, remap = false)
public class AdditionalEnchantmentDataProviderMixin {

    @Unique
    private final AdditionalEnchantmentDataProvider self = (AdditionalEnchantmentDataProvider) (Object) this;

    @Inject(
            method = "getEnchantmentDataEntries",
            at = @At(
                    value = "INVOKE",
                    target = "Lfuzs/universalenchants/world/item/enchantment/data/AdditionalEnchantmentDataProvider;setupAdditionalCompatibility(Ljava/util/Map;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void modifyBowEnchantments(CallbackInfoReturnable<Map<Enchantment, List<DataEntry<?>>>> cir, @Local Map<Enchantment, DataEntry.Builder> builders) {

        // Ensure Multishot is not compatible with Bow
        if (builders.containsKey(Enchantments.MULTISHOT)) {
            var builderAccessor = (DataEntryBuilderAccessor)(Object)builders.get(Enchantments.MULTISHOT);
            var entries = builderAccessor.getEntries();
            entries.removeIf(entry -> {
                if (entry instanceof TypeEntry.CategoryEntry categoryEntry && !categoryEntry.isExclude()) {
                    var jsonArray = new JsonArray();
                    entry.serialize(jsonArray);
                    var id = jsonArray.get(0).getAsString();
                    var bowId = "$minecraft:bow";
                    if (Objects.equals(id, bowId)) return true;
                }
                return false;
            });
        }
    }

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
