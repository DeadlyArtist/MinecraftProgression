package com.prog.data;

import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.itemOrBlock.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public static final Map<TagKey<Item>, ArrayList<Item>> tags = new HashMap<>();

    public PItemTagProvider(FabricDataGenerator dataGenerator, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(dataGenerator, blockTagProvider);
    }

    public static void addToTag(TagKey<Item> tag, Item item) {
        tags.computeIfAbsent(tag, k -> new ArrayList<>()).add(item);
    }

    public static void addToUpgrades(Item item, Function<Item, List<UEffect>> effects) {
        addToTag(PItemTags.UPGRADE, item);
        Upgrades.register(item, effects);
    }

    public static void addToGourmetFood(Item item, List<UEffect> effects) {
        addToTag(PItemTags.GOURMET_FOOD, item);
        GourmetFoods.register(item, effects);
    }

    public static void addToGourmetFood(Item item, UEffect effect) {
        addToTag(PItemTags.GOURMET_FOOD, item);
        GourmetFoods.register(item, List.of(effect));
    }

    @Override
    protected void generateTags() {
        // Vanilla upgrade overrides
        addToUpgrades(Items.PRISMARINE_SHARD, UEffectMapper.damage());
        addToUpgrades(Items.MAGMA_CREAM, UEffectMapper.best());
        addToUpgrades(Items.HONEY_BLOCK, UEffectMapper.boots(UEffect.add(PEntityAttributes.FALL_DAMAGE_DIVISOR, 2)));
        addToUpgrades(Items.PHANTOM_MEMBRANE, UEffectMapper.best());
        addToUpgrades(Items.WITHER_ROSE, UEffectMapper.best());
        addToUpgrades(Items.HEART_OF_THE_SEA, UEffectMapper.best());
        addToUpgrades(Items.EMERALD, UEffectMapper.best());
        addToUpgrades(Items.MUSIC_DISC_PIGSTEP, UEffectMapper.best());
        addToUpgrades(Items.SCUTE, UEffectMapper.best());
        addToUpgrades(Items.SLIME_BALL, UEffectMapper.boots(UEffect.add(PEntityAttributes.FALL_DAMAGE_DIVISOR, 2)));
        addToUpgrades(Items.ENDER_EYE, UEffectMapper.best());
        addToUpgrades(Items.TOTEM_OF_UNDYING, UEffectMapper.protection());
        addToUpgrades(Items.GOAT_HORN, UEffectMapper.chestplate(UEffect.add(XEntityAttributes.LUNG_CAPACITY, 5)));
        addToUpgrades(Items.CREEPER_HEAD, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.ZOMBIE_HEAD, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.SKELETON_SKULL, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.WITHER_SKELETON_SKULL, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.NETHER_STAR, UEffectMapper.best(2));

        // Vanilla gourmet food overrides
        addToGourmetFood(Items.GOLDEN_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        addToGourmetFood(Items.ENCHANTED_GOLDEN_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 5));
        addToGourmetFood(Items.CHORUS_FRUIT, UEffect.increment(XEntityAttributes.JUMP_HEIGHT));
        addToGourmetFood(Items.POISONOUS_POTATO, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        addToGourmetFood(Items.GOLDEN_CARROT, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        addToGourmetFood(Items.GLISTERING_MELON_SLICE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        addToGourmetFood(Items.ROTTEN_FLESH, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        addToGourmetFood(Items.SPIDER_EYE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));

        // Preregistered
        PItems.data.forEach((item, data) -> data.tags.forEach(tag -> addToTag(tag, item)));

        // Add all tags
        tags.forEach((tag, items) -> {
            FabricTagProvider<Item>.FabricTagBuilder<Item> tagBuilder = getOrCreateTagBuilder(tag);
            items.forEach(item -> tagBuilder.add(item));
        });
    }
}
