package com.prog.utils;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class DimensionUtils {
    public static RegistryKey<World> getWorld(Identifier id) {
        return RegistryKey.of(Registry.WORLD_KEY, id);
    }

    public static RegistryKey<World> getWorld(String id) {
        return getWorld(new Identifier(id));
    }
}
