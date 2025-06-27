package com.prog.utils;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class GlobalPosUtils {
    public static GlobalPos fromNbt(NbtCompound nbt, String key) {
        if (!nbt.contains(key)) return null;
        var part = nbt.getCompound(key);
        return GlobalPos.create(DimensionUtils.getWorld(part.getString("dimension")), BlockPos.fromLong(part.getLong("position")));
    }

    public static void toNbt(NbtCompound nbt, String key, GlobalPos pos) {
        var part = new NbtCompound();
        part.putString("dimension", pos.getDimension().getValue().toString());
        part.putLong("position", pos.getPos().asLong());
        nbt.put(key, part);
    }
}
