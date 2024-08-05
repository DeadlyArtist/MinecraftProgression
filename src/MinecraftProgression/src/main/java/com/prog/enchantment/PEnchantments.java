package com.prog.enchantment;

import com.prog.Prog;
import com.prog.itemOrBlock.PItemGroups;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class PEnchantments {
    public static class EnchantmentData {
        public String name;

        public EnchantmentData(String name) {
            this.name = name;
        }
    }

    public static final Map<Enchantment, EnchantmentData> data = new HashMap<>();

    public static Enchantment AIR_AFFINITY = register("AIR_AFFINITY",
            new AirAffinityEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.HEAD));


    private static Enchantment register(String id, Enchantment enchantment) {
        id = id.toLowerCase();
        String name = StringUtils.toNormalCase(id);
        var registeredEnchantment = Registry.register(Registry.ENCHANTMENT, new Identifier(Prog.MOD_ID, id.toLowerCase()), enchantment);
        data.put(registeredEnchantment, new EnchantmentData(name));
        return registeredEnchantment;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Enchantments for: " + Prog.MOD_ID);
    }
}
