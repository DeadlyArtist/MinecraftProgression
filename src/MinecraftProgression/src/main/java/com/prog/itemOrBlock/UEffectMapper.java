package com.prog.itemOrBlock;

import com.prog.entity.attribute.XEntityAttributes;
import com.prog.utils.EntityAttributeModifierUtils;
import com.prog.utils.ItemUtils;
import com.prog.utils.ListUtils;
import com.prog.utils.SlotUtils;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UEffectMapper {
    public static Function<Item, List<UEffect>> all(List<UEffect> effects) {
        return item -> effects;
    }

    public static Function<Item, List<UEffect>> byType(List<UEffect> armorEffects, List<UEffect> toolEffects, List<UEffect> rangedEffects, List<UEffect> otherEffects) {
        var tridentEffects = ListUtils.ofMany(toolEffects, rangedEffects);
        return item -> {
            if (ItemUtils.isArmor(item)) return armorEffects;
            else if (ItemUtils.isTool(item)) return toolEffects;
            else if (ItemUtils.isTrident(item)) return tridentEffects;
            else if (ItemUtils.isRanged(item)) return rangedEffects;
            else return otherEffects;
        };
    }

    public static Function<Item, List<UEffect>> byType(UEffect armorEffect, UEffect toolEffect, UEffect rangedEffect, UEffect otherEffect) {
        return byType(List.of(armorEffect), List.of(toolEffect), List.of(rangedEffect), List.of(otherEffect));
    }

    public static Function<Item, List<UEffect>> best(double amount) {
        return armorMeleeRanged(UEffect.add(EntityAttributes.GENERIC_ARMOR, amount), UEffect.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, amount), UEffect.add(XEntityAttributes.PROJECTILE_DAMAGE, amount));
    }

    public static Function<Item, List<UEffect>> best() {
        return best(1);
    }

    public static Function<Item, List<UEffect>> melee(List<UEffect> effects) {
        return byType(null, effects, null, effects);
    }

    public static Function<Item, List<UEffect>> melee(UEffect effect) {
        return melee(List.of(effect));
    }

    public static Function<Item, List<UEffect>> meleeRanged(List<UEffect> meleeEffects, List<UEffect> rangedEffects) {
        return byType(null, meleeEffects, rangedEffects, meleeEffects);
    }

    public static Function<Item, List<UEffect>> meleeRanged(UEffect meleeEffect, UEffect rangedEffect) {
        return meleeRanged(List.of(meleeEffect), List.of(rangedEffect));
    }

    public static Function<Item, List<UEffect>> armorMeleeRanged(List<UEffect> armorEffects, List<UEffect> meleeEffects, List<UEffect> rangedEffects) {
        return byType(armorEffects, meleeEffects, rangedEffects, meleeEffects);
    }

    public static Function<Item, List<UEffect>> armorMeleeRanged(UEffect armorEffect, UEffect meleeEffect, UEffect rangedEffect) {
        return armorMeleeRanged(List.of(armorEffect), List.of(meleeEffect), List.of(rangedEffect));
    }

    public static Function<Item, List<UEffect>> damage(double amount) {
        return meleeRanged(UEffect.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, amount), UEffect.add(XEntityAttributes.PROJECTILE_DAMAGE, amount));
    }

    public static Function<Item, List<UEffect>> damage() {
        return damage(1);
    }

    public static Function<Item, List<UEffect>> protection(double amount) {
        return armor(UEffect.add(EntityAttributes.GENERIC_ARMOR, amount));
    }

    public static Function<Item, List<UEffect>> protection() {
        return protection(1);
    }

    public static Function<Item, List<UEffect>> tool(List<UEffect> effects) {
        return item -> (ItemUtils.isTool(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> tool(UEffect effect) {
        return tool(List.of(effect));
    }

    public static Function<Item, List<UEffect>> armor(List<UEffect> effects) {
        return item -> (ItemUtils.isArmor(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> armor(UEffect effect) {
        return armor(List.of(effect));
    }

    public static Function<Item, List<UEffect>> ranged(List<UEffect> effects) {
        return item -> (ItemUtils.isRanged(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> ranged(UEffect effect) {
        return ranged(List.of(effect));
    }

    public static Function<Item, List<UEffect>> trident(List<UEffect> effects) {
        return item -> (ItemUtils.isTrident(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> trident(UEffect effect) {
        return trident(List.of(effect));
    }

    public static Function<Item, List<UEffect>> boots(List<UEffect> effects) {
        return item -> (ItemUtils.isBoots(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> boots(UEffect effect) {
        return boots(List.of(effect));
    }

    public static Function<Item, List<UEffect>> helmet(List<UEffect> effects) {
        return item -> (ItemUtils.isHelmet(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> helmet(UEffect effect) {
        return helmet(List.of(effect));
    }

    public static Function<Item, List<UEffect>> chestplate(List<UEffect> effects) {
        return item -> (ItemUtils.isChestplate(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> chestplate(UEffect effect) {
        return chestplate(List.of(effect));
    }

    public static Function<Item, List<UEffect>> leggings(List<UEffect> effects) {
        return item -> (ItemUtils.isLeggings(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> leggings(UEffect effect) {
        return leggings(List.of(effect));
    }

    public static Function<Item, List<UEffect>> axe(List<UEffect> effects) {
        return item -> (ItemUtils.isAxe(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> axe(UEffect effect) {
        return axe(List.of(effect));
    }

    public static Function<Item, List<UEffect>> hoe(List<UEffect> effects) {
        return item -> (ItemUtils.isHoe(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> hoe(UEffect effect) {
        return hoe(List.of(effect));
    }

    public static Function<Item, List<UEffect>> pickaxe(List<UEffect> effects) {
        return item -> (ItemUtils.isPickaxe(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> pickaxe(UEffect effect) {
        return pickaxe(List.of(effect));
    }

    public static Function<Item, List<UEffect>> shovel(List<UEffect> effects) {
        return item -> (ItemUtils.isShovel(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> shovel(UEffect effect) {
        return shovel(List.of(effect));
    }

    public static Function<Item, List<UEffect>> sword(List<UEffect> effects) {
        return item -> (ItemUtils.isSword(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> sword(UEffect effect) {
        return sword(List.of(effect));
    }

    public static Function<Item, List<UEffect>> bow(List<UEffect> effects) {
        return item -> (ItemUtils.isBow(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> bow(UEffect effect) {
        return bow(List.of(effect));
    }

    public static Function<Item, List<UEffect>> crossbow(List<UEffect> effects) {
        return item -> (ItemUtils.isCrossbow(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> crossbow(UEffect effect) {
        return crossbow(List.of(effect));
    }

    public static Function<Item, List<UEffect>> fishingRod(List<UEffect> effects) {
        return item -> (ItemUtils.isFishingRod(item)) ? effects : null;
    }

    public static Function<Item, List<UEffect>> fishingRod(UEffect effect) {
        return fishingRod(List.of(effect));
    }
}
