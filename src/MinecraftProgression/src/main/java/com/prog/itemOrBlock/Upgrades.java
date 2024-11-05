package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.utils.LOGGER;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Upgrades {
    public static final Map<Item, Upgrade> data = new HashMap<>();

    static {
        // Vanilla upgrade overrides
        register(Items.END_CRYSTAL, UEffectMapper.damage());
        register(Items.PRISMARINE_SHARD, UEffectMapper.damage());
        register(Items.AMETHYST_SHARD, UEffectMapper.damage());
        register(Items.ECHO_SHARD, UEffectMapper.damage());
        register(Items.MAGMA_CREAM, UEffectMapper.best());
        register(Items.GHAST_TEAR, List.of(UEffectMapper.best(), UEffectMapper.helmet(UEffect.add(XEntityAttributes.LAVA_VISIBILITY, 100))));
        register(Items.HONEY_BLOCK, UEffectMapper.boots(UEffect.add(PEntityAttributes.IMPACT_ABSORPTION, 2)));
        register(Items.PHANTOM_MEMBRANE, UEffectMapper.best());
        register(Items.WITHER_ROSE, UEffectMapper.best());
        register(Items.HEART_OF_THE_SEA, List.of(UEffectMapper.best(2), UEffectMapper.chestplate(UEffect.add(XEntityAttributes.WATER_VISIBILITY, 50))));
        register(Items.EMERALD, UEffectMapper.best());
        register(Items.LAPIS_LAZULI, UEffectMapper.best());
        register(Items.MUSIC_DISC_PIGSTEP, UEffectMapper.best());
        register(Items.SCUTE, UEffectMapper.best());
        register(Items.TURTLE_HELMET, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.SLIME_BALL, UEffectMapper.boots(UEffect.add(PEntityAttributes.IMPACT_ABSORPTION, 1)));
        register(Items.SLIME_BLOCK, UEffectMapper.boots(UEffect.add(PEntityAttributes.IMPACT_ABSORPTION, 1)));
        register(Items.ENDER_PEARL, UEffectMapper.best());
        register(Items.ENDER_EYE, UEffectMapper.best());
        register(Items.TOTEM_OF_UNDYING, UEffectMapper.protection());
        register(Items.WHITE_BANNER, UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.BAD_OMEN_IMMUNITY)));
        register(Items.LODESTONE, UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.BLINDNESS_IMMUNITY)));
        register(Items.GOAT_HORN, UEffectMapper.chestplate(UEffect.add(XEntityAttributes.LUNG_CAPACITY, 5)));
        register(Items.CREEPER_HEAD, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.ZOMBIE_HEAD, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.SKELETON_SKULL, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.WITHER_SKELETON_SKULL, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.NETHER_STAR, UEffectMapper.best(2));
        register(Items.TORCH, UEffectMapper.helmet(UEffect.add(PEntityAttributes.LUMINANCE, 14)));
        register(Items.GOLDEN_BOOTS, UEffectMapper.boots(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25)));
        register(Items.GOLDEN_CHESTPLATE, UEffectMapper.chestplate(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25)));
        register(Items.GOLDEN_HELMET, UEffectMapper.helmet(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25)));
        register(Items.GOLDEN_LEGGINGS, UEffectMapper.leggings(UEffect.add(PEntityAttributes.PIGLIN_LOVED, 0.25))); // All 4 gold armor items required for piglin loved to take effect
        register(Items.GOLDEN_AXE, UEffectMapper.axe(UEffect.increment(XEntityAttributes.DIG_SPEED)));
        register(Items.GOLDEN_HOE, UEffectMapper.hoe(UEffect.increment(XEntityAttributes.DIG_SPEED)));
        register(Items.GOLDEN_PICKAXE, UEffectMapper.pickaxe(UEffect.increment(XEntityAttributes.DIG_SPEED)));
        register(Items.GOLDEN_SHOVEL, UEffectMapper.shovel(UEffect.increment(XEntityAttributes.DIG_SPEED)));
        register(Items.GOLDEN_SWORD, UEffectMapper.sword(UEffect.add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.1)));
        register(Items.CHAINMAIL_BOOTS, UEffectMapper.boots(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.CHAINMAIL_CHESTPLATE, UEffectMapper.chestplate(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.CHAINMAIL_HELMET, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.CHAINMAIL_LEGGINGS, UEffectMapper.leggings(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.LEATHER_BOOTS, UEffectMapper.boots(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.LEATHER_CHESTPLATE, UEffectMapper.chestplate(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.LEATHER_HELMET, UEffectMapper.helmet(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.LEATHER_LEGGINGS, UEffectMapper.leggings(UEffect.increment(EntityAttributes.GENERIC_ARMOR)));
        register(Items.NAUTILUS_SHELL, UEffectMapper.protection());
        register(Items.SNOWBALL, UEffectMapper.bow(UEffect.increment(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        register(Items.FIREWORK_ROCKET, List.of(UEffectMapper.ranged(UEffect.increment(PEntityAttributes.PROJECTILE_SPEED)), UEffectMapper.boots(UEffect.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05)), UEffectMapper.chestplate(UEffect.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.05))));
        register(Items.FIRE_CHARGE, UEffectMapper.damage());
        register(Items.GLOW_BERRIES, List.of(UEffectMapper.tool(UEffect.increment(EntityAttributes.GENERIC_LUCK)), UEffectMapper.fishingRod(UEffect.increment(EntityAttributes.GENERIC_LUCK))));
        register(Items.GLOW_INK_SAC, List.of(UEffectMapper.tool(UEffect.increment(EntityAttributes.GENERIC_LUCK)), UEffectMapper.fishingRod(UEffect.increment(EntityAttributes.GENERIC_LUCK))));
        register(Items.DRAGON_BREATH, UEffectMapper.best());
        register(Items.DISC_FRAGMENT_5, UEffectMapper.damage());
        register(Items.FEATHER, UEffectMapper.boots(UEffect.add(PEntityAttributes.LIGHTNESS, 0.5)));
        register(Items.FERMENTED_SPIDER_EYE, UEffectMapper.helmet(UEffect.add(XEntityAttributes.WATER_VISIBILITY, 20)));
        register(Items.CONDUIT, UEffectMapper.damage(2));
        register(Items.SHULKER_SHELL, UEffectMapper.protection());
        register(Items.HONEYCOMB, UEffectMapper.protection());
        register(Items.RABBIT_FOOT, UEffectMapper.boots(UEffect.increment(XEntityAttributes.BONUS_RARE_LOOT_ROLLS)));
        register(Items.CARVED_PUMPKIN, UEffectMapper.helmet(UEffect.increment(PEntityAttributes.ENDERMAN_DISGUISE)));

        // Custom
        register(PItems.TELEPORTATION_CORE, UEffectMapper.ranged(UEffect.increment(PEntityAttributes.PROJECTILE_SPEED)));
        register(PItems.MECHANICAL_BOOTS, UEffectMapper.boots(UEffect.increment(PEntityAttributes.STEP_HEIGHT)));
        register(PJetpacks.MECHANICAL.item.get(), UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.JETPACK)));
        register(PItems.ANGEL_RING, UEffectMapper.chestplate(UEffect.add(PEntityAttributes.LIGHTNESS, 0.5)));
        register(PItems.ANCHOR, UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.LEVITATION_IMMUNITY)));
        register(PItems.DREAM_CATCHER, UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.INSOMNIA_IMMUNITY)));
        register(PItems.OCEANS_GRACE, UEffectMapper.helmet(UEffect.increment(PEntityAttributes.MINING_FATIGUE_IMMUNITY)));
        register(PItems.LIVING_SOUL_FRAGMENT, UEffectMapper.best());
        register(PItems.SILENT_HEART, UEffectMapper.chestplate(UEffect.increment(PEntityAttributes.DARKNESS_IMMUNITY)));
    }

    public static Upgrade register(Item item, Function<Item, List<UEffect>> effects) {
        Upgrade upgrade = Upgrade.of(item, effects);
        data.put(item, upgrade);
        return upgrade;
    }

    public static void register(Item item, List<Function<Item, List<UEffect>>> effects) {
        Function<Item, List<UEffect>> func = target ->
                effects.stream()
                        .map(f -> Optional.ofNullable(f.apply(target)).orElse(Collections.emptyList()))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
        Upgrades.register(item, func);
    }

    public static void init() {
        LOGGER.info("Registering Upgrades for: " + Prog.MOD_ID);
    }
}
