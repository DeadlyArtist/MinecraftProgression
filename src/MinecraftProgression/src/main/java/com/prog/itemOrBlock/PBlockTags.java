package com.prog.itemOrBlock;

import com.prog.IDRef;
import com.prog.Prog;
import com.prog.itemOrBlock.tiers.PMiningLevels;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class PBlockTags {
    // Mining levels
    public static final TagKey<Block> NEEDS_REFINED_OBSIDIAN_TOOL = createExternalTag(IDRef.FABRIC, getToolLevelPath(PMiningLevels.REFINED_OBSIDIAN));
    public static final TagKey<Block> NEEDS_TITAN_TOOL = createExternalTag(IDRef.FABRIC, getToolLevelPath(PMiningLevels.TITAN));
    public static final TagKey<Block> NEEDS_PRIMAL_NETHERITE_TOOL = createExternalTag(IDRef.FABRIC, getToolLevelPath(PMiningLevels.PRIMAL_NETHERITE));

    // Mining level must be at least 4
    public static String getToolLevelPath(int miningLevel){
        return "needs_tool_level_" + miningLevel;
    }

    private static TagKey<Block> createTag(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(Prog.MOD_ID, id.toLowerCase()));
    }

    private static TagKey<Block> createExternalTag(String namespace, String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(namespace, id.toLowerCase()));
    }

    public static void init() {
        Prog.LOGGER.info("Registering Block Tags for: " + Prog.MOD_ID);
    }
}
