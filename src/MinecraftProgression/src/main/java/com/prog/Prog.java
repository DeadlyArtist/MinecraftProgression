package com.prog;

import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.kwpugh.ring_of_attraction.util.MagnetUtil;
import com.prog.data.PItemTagProvider;
import com.prog.data.PKeybindingLangHelper;
import com.prog.data.PRecipeProvider;
import com.prog.enchantment.PEnchantments;
import com.prog.entity.PComponents;
import com.prog.entity.PEntityLootTables;
import com.prog.entity.PStatusEffects;
import com.prog.entity.attribute.PDefaultAttributes;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.event.*;
import com.prog.itemOrBlock.*;
import com.prog.itemOrBlock.custom.TieredBowItem;
import com.prog.itemOrBlock.custom.TieredCrossbowItem;
import com.prog.itemOrBlock.custom.TieredTridentItem;
import com.prog.network.PNetwork;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.text.PTexts;
import com.prog.utils.*;
import com.prog.world.OreGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.nbt.NbtByte;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.RegistryEntry;
import net.purejosh.froglegs.init.FroglegsModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

import static com.prog.entity.attribute.PEntityAttributes.IMMUNITY_MAP;

public class Prog implements ModInitializer {
    public static final String MOD_ID = "prog";
    public static final String VERSION = "1.0.5";
    public static final String NAME = "More Progression";
    public static final Logger __LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Up Mod: " + MOD_ID);

        // Order matters maybe
        PTexts.init();
        PRecipeTypes.init();
        PRecipeSerializers.init();
        PItemTags.init();
        PBlockTags.init();
        PEnchantments.init();
        PItemGroups.init();
        PItems.init();
        PBlocks.init();
        PBlockEntityTypes.init();
        PEntityAttributes.init();
        PDefaultAttributes.init();
        PStatusEffects.init();
        GourmetFoods.init();
        OreGeneration.init();
        PEntityLootTables.init();
        PNetwork.init();
        PKeybindingLangHelper.init();
        PItemTagProvider.initTags();

        // Events
        //ServerTickEvents.START_WORLD_TICK.register(server -> LOGGER.info("WORLD"));
        TagEvents.TAG_LOADED.register((tagId, entries) -> {
            if (tagId.equals(BlockTags.NEEDS_DIAMOND_TOOL.id())) {
                Collection<RegistryEntry<Block>> blockEntries = (Collection<RegistryEntry<Block>>) entries;
                blockEntries.remove(Blocks.ANCIENT_DEBRIS.getRegistryEntry());
            }

            if (tagId.equals(PItemTags.GOURMET_FOOD.id())) {
                GourmetFoods.registerAllCompat();
                Collection<RegistryEntry<Item>> itemEntries = (Collection<RegistryEntry<Item>>) entries;
                GourmetFoods.compatData.forEach(item -> itemEntries.add(item.getRegistryEntry()));
            }

            if (tagId.equals(PItemTags.UPGRADE.id())) {
                Upgrades.registerAllCompat();
                Collection<RegistryEntry<Item>> itemEntries = (Collection<RegistryEntry<Item>>) entries;
                Upgrades.compatData.forEach(item -> itemEntries.add(item.getRegistryEntry()));
            }
        });

        RecipeEvents.RECIPES_LOADED.register(map -> {
            // Upgrades
            Upgrades.data.forEach((item, upgrade) -> {
                PRecipeProvider.getUpgradeRecipes(upgrade).forEach(wrapper -> wrapper.offer(provider -> map.put(wrapper.getId(), provider.toJson())));
            });


            // Minecraft modding really does not seem to be made for compat.
//            if (XCompat.isModLoaded(XIDs.SUPPLEMENTARIES)) {
//                Supplier<?> wrapper = ModRegistry.BOMB_BLUE_ITEM;
//                var upgrade = Upgrades.register((Item)(Object)wrapper.get(), UEffectMapper.damage());
//                PRecipeProvider.getUpgradeRecipes(upgrade).forEach(builder -> builder.offer(provider -> map.put(builder.getId(), provider.toJson())));
//            }
        });

        ItemEvents.APPEND_STACKS.register(((group, stacks, item) -> {
            if (group == PItemGroups.MORE_PROGRESSION) {
                if (ItemUtils.getId(item).getNamespace() == Prog.MOD_ID) stacks.add(new ItemStack(item));
            }
            if (group == PItemGroups.UPGRADABLES) {
                if (ItemUtils.hasTag(item, PItemTags.UPGRADABLE)) stacks.add(new ItemStack(item));
            }
            if (group == PItemGroups.TIER_CORES) {
                if (ItemUtils.hasTag(item, PItemTags.TIER_CORE)) stacks.add(new ItemStack(item));
            }
            if (group == PItemGroups.UPGRADES) {
                if (ItemUtils.hasTag(item, PItemTags.UPGRADE)) stacks.add(new ItemStack(item));
            }
            if (group == PItemGroups.GOURMET_FOOD) {
                if (ItemUtils.hasTag(item, PItemTags.GOURMET_FOOD)) stacks.add(new ItemStack(item));
            }
        }));

        EntityEvents.LIVING_ENTITY_TICK.register(entity -> {
            entity.stepHeight = (float) ((entity instanceof PlayerEntity && PComponents.PLAYER.get(entity).stepAssistDisabled) ? PEntityAttributes.STEP_HEIGHT.getDefaultValue() : entity.getAttributeValue(PEntityAttributes.STEP_HEIGHT));
        });

        EntityEvents.PLAYER_ENTITY_TICK.register(player -> {
            PComponents.PLAYER.get(player).updateFlight();
            JetpackUtils.tickJetpack(player);

            if (player.getAttributeValue(PEntityAttributes.MAGNET) == 1)
                MagnetUtil.doMagnet(player.world, player, null);
        });

        ItemStackEvents.ITEM_STACK_CTOR.register((stack) -> {
            if (stack.isIn(PItemTags.UPGRADABLE)) {
                stack.setSubNbt(ItemStack.UNBREAKABLE_KEY, NbtByte.ONE);
                if (stack.getDamage() > 0) {
                    stack.setDamage(0);
                }
            }
        });

        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) -> {
            if (!SlotUtils.isEquipped(stack, slot)) return;

            var item = stack.getItem();

            var upgrades = UpgradeUtils.extractUpgradeData(stack);
            upgrades.forEach((name, effects) -> effects.forEach(effect -> attributeModifiers.put(effect.target, effect.modifier)));


            if (item instanceof SwordItem && stack.isIn(PItemTags.TITAN_OR_HIGHER)) {
                attributeModifiers.put(XEntityAttributes.ATTACK_RANGE, EntityAttributeModifierUtils.increment("default_attack_range_increase_1"));
            }

            if (item instanceof ArmorItem armorItem && armorItem.getSlotType() == EquipmentSlot.CHEST && stack.isIn(PItemTags.VERUM_OR_HIGHER)) {
                attributeModifiers.put(PEntityAttributes.FLIGHT, EntityAttributeModifierUtils.increment("default_flight"));
            }

            var projectileDamage = 0D;
            if (item instanceof TridentItem) {
                projectileDamage = RangedUtils.BASE_TRIDENT_RANGED_DAMAGE;
                if (item instanceof TieredTridentItem tiered) {
                    projectileDamage += tiered.material.getDamageBonus();
                }
            } else if (item instanceof BowItem) {
                projectileDamage = RangedUtils.BASE_BOW_RANGED_DAMAGE;
                if (item instanceof TieredBowItem tiered) {
                    projectileDamage += tiered.material.getProjectileDamageBonus();
                }
            } else if (item instanceof CrossbowItem) {
                projectileDamage = RangedUtils.BASE_CROSSBOW_RANGED_DAMAGE;
                if (item instanceof TieredCrossbowItem tiered) {
                    projectileDamage += tiered.material.getProjectileDamageBonus();
                }
            }

            if (projectileDamage != 0) {
                attributeModifiers.put(PEntityAttributes.PROJECTILE_DAMAGE, new EntityAttributeModifier(RangedUtils.PROJECTILE_DAMAGE_BASE_MODIFIER_ID, "PROJECTILE_DAMAGE_BASE_MODIFIER", projectileDamage, EntityAttributeModifier.Operation.ADDITION));
            }

//            Example
//            if (stack.isOf(Items.DIAMOND_HELMET) && slot.getEntitySlotId() == HEAD_SLOT_ID) {
//                attributeModifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, MODIFIER);
//            }
        });

        EntityEvents.APPLY_FOOD_EFFECTS.register((entity, stack) -> {
            PComponents.LIVING_ENTITY.get(entity).eat(stack.getItem());
        });

        EntityEvents.CAN_HAVE_STATUS_EFFECT.register((entity, effectInstance) -> {
            StatusEffect effect = effectInstance.getEffectType();
            var immunityAttribute = IMMUNITY_MAP.get(effect);
            if (immunityAttribute != null && entity.getAttributeValue(immunityAttribute) == 1.0) {
                if (immunityAttribute == PEntityAttributes.BAD_OMEN_IMMUNITY && entity instanceof PlayerEntity && PComponents.PLAYER.get(entity).badOmenImmunityDisabled) return true;

                return false;
            }

            return true;
        });

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && EntityType.WARDEN.getLootTableId().equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(PItems.LIVING_SOUL_FRAGMENT));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}