package com.prog.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.Structures;

public class StructureUtils {
    public static Pair<BlockPos, RegistryEntry<Structure>> locate(ServerWorld serverWorld, RegistryEntry<Structure> structure, BlockPos searchPos, int radius, boolean skipReferencedStructure) {
        return serverWorld.getChunkManager().getChunkGenerator().locateStructure(serverWorld, RegistryEntryList.of(serverWorld.getRegistryManager().get(Registry.STRUCTURE_KEY).getEntry(structure.getKey().orElseThrow()).orElseThrow()), searchPos, radius, skipReferencedStructure);
    }
}
