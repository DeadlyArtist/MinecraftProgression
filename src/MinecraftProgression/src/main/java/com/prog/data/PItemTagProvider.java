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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static void addToUpgrades(Item item, List<Function<Item, List<UEffect>>> effects) {
        addToTag(PItemTags.UPGRADE, item);
        Function<Item, List<UEffect>> func = target ->
                effects.stream()
                        .map(f -> Optional.ofNullable(f.apply(target)).orElse(Collections.emptyList()))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
        Upgrades.register(item, func);
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
        addToUpgrades(Items.END_CRYSTAL, UEffectMapper.damage());
        addToUpgrades(Items.PRISMARINE_SHARD, UEffectMapper.damage());
        addToUpgrades(Items.AMETHYST_SHARD, UEffectMapper.damage());
        addToUpgrades(Items.ECHO_SHARD, UEffectMapper.damage());
        addToUpgrades(Items.MAGMA_CREAM, UEffectMapper.best());
        addToUpgrades(Items.GHAST_TEAR, List.of(UEffectMapper.best(), UEffectMapper.helmet(UEffect.add(XEntityAttributes.LAVA_VISIBILITY, 100))));
        addToUpgrades(Items.HONEY_BLOCK, UEffectMapper.boots(UEffect.add(PEntityAttributes.FALL_DAMAGE_DIVISOR, 2)));
        addToUpgrades(Items.PHANTOM_MEMBRANE, UEffectMapper.best());
        addToUpgrades(Items.WITHER_ROSE, UEffectMapper.best());
        addToUpgrades(Items.HEART_OF_THE_SEA, List.of(UEffectMapper.best(2), UEffectMapper.chestplate(UEffect.add(XEntityAttributes.WATER_VISIBILITY, 50))));
        addToUpgrades(Items.EMERALD, UEffectMapper.best());
        addToUpgrades(Items.LAPIS_LAZULI, UEffectMapper.best());
        addToUpgrades(Items.MUSIC_DISC_PIGSTEP, UEffectMapper.best());
        addToUpgrades(Items.SCUTE, UEffectMapper.best());
        addToUpgrades(Items.SLIME_BALL, UEffectMapper.boots(UEffect.add(PEntityAttributes.FALL_DAMAGE_DIVISOR, 2)));
        addToUpgrades(Items.ENDER_PEARL, UEffectMapper.best());
        addToUpgrades(Items.ENDER_EYE, UEffectMapper.best());
        addToUpgrades(Items.TOTEM_OF_UNDYING, UEffectMapper.protection());
        addToUpgrades(Items.GOAT_HORN, UEffectMapper.chestplate(UEffect.add(XEntityAttributes.LUNG_CAPACITY, 5)));
        addToUpgrades(Items.CREEPER_HEAD, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.ZOMBIE_HEAD, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.SKELETON_SKULL, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.WITHER_SKELETON_SKULL, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.NETHER_STAR, UEffectMapper.best(2));
        addToUpgrades(Items.TORCH, UEffectMapper.helmet(UEffect.add(PEntityAttributes.LUMINANCE, 14)));
        addToUpgrades(Items.GOLDEN_BOOTS, UEffectMapper.boots(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25)));
        addToUpgrades(Items.GOLDEN_CHESTPLATE, UEffectMapper.chestplate(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25)));
        addToUpgrades(Items.GOLDEN_HELMET, UEffectMapper.helmet(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25)));
        addToUpgrades(Items.GOLDEN_LEGGINGS, UEffectMapper.leggings(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25))); // All 4 gold armor items required for piglin loved to take effect
        addToUpgrades(Items.GOLDEN_AXE, UEffectMapper.axe(UEffect.add(XEntityAttributes.DIG_SPEED, 0.1)));
        addToUpgrades(Items.GOLDEN_HOE, UEffectMapper.hoe(UEffect.add(XEntityAttributes.DIG_SPEED, 0.1)));
        addToUpgrades(Items.GOLDEN_PICKAXE, UEffectMapper.pickaxe(UEffect.add(XEntityAttributes.DIG_SPEED, 0.1)));
        addToUpgrades(Items.GOLDEN_SHOVEL, UEffectMapper.shovel(UEffect.add(XEntityAttributes.DIG_SPEED, 0.1)));
        addToUpgrades(Items.GOLDEN_SWORD, UEffectMapper.sword(UEffect.add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.1)));
        addToUpgrades(Items.CHAINMAIL_BOOTS, UEffectMapper.boots(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.CHAINMAIL_CHESTPLATE, UEffectMapper.chestplate(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.CHAINMAIL_HELMET, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.CHAINMAIL_LEGGINGS, UEffectMapper.leggings(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        addToUpgrades(Items.NAUTILUS_SHELL, UEffectMapper.protection());
        addToUpgrades(Items.SNOWBALL, UEffectMapper.bow(UEffect.increment(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        addToUpgrades(Items.FIREWORK_ROCKET, List.of(UEffectMapper.ranged(UEffect.increment(PEntityAttributes.PROJECTILE_SPEED)), UEffectMapper.boots(UEffect.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05)), UEffectMapper.chestplate(UEffect.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.05))));
        addToUpgrades(Items.FIRE_CHARGE, UEffectMapper.damage());
        addToUpgrades(Items.GLOW_BERRIES, UEffectMapper.fishingRod(UEffect.increment(EntityAttributes.GENERIC_LUCK)));
        addToUpgrades(Items.DRAGON_BREATH, UEffectMapper.best());
        addToUpgrades(Items.DISC_FRAGMENT_5, UEffectMapper.damage());
        addToUpgrades(PItems.TELEPORTATION_CORE, UEffectMapper.ranged(UEffect.increment(PEntityAttributes.PROJECTILE_SPEED)));
        addToUpgrades(Items.FEATHER, UEffectMapper.boots(UEffect.add(PEntityAttributes.FALL_DAMAGE_DIVISOR, 0.5)));
        addToUpgrades(Items.FERMENTED_SPIDER_EYE, UEffectMapper.helmet(UEffect.add(XEntityAttributes.WATER_VISIBILITY, 20)));
        addToUpgrades(Items.CONDUIT, UEffectMapper.damage(2));
        addToUpgrades(Items.SHULKER_SHELL, UEffectMapper.protection());

        // Preregistered
        PItems.data.forEach((item, data) -> data.tags.forEach(tag -> addToTag(tag, item)));
        GourmetFoods.data.forEach((item, data) -> addToTag(PItemTags.GOURMET_FOOD, item));

        // Add all tags
        tags.forEach((tag, items) -> {
            FabricTagProvider<Item>.FabricTagBuilder<Item> tagBuilder = getOrCreateTagBuilder(tag);
            items.forEach(item -> tagBuilder.add(item));
        });
    }
}
