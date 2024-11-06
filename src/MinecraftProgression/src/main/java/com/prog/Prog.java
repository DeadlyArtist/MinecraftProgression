package com.prog;

import com.prog.data.PRecipeProvider;
import com.prog.enchantment.PEnchantments;
import com.prog.entity.PComponents;
import com.prog.entity.PEntityLootTables;
import com.prog.entity.PStatusEffects;
import com.prog.entity.attribute.PDefaultAttributes;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.event.EntityEvents;
import com.prog.event.ItemStackEvents;
import com.prog.event.RecipeEvents;
import com.prog.event.TagEvents;
import com.prog.itemOrBlock.*;
import com.prog.itemOrBlock.custom.TieredTridentItem;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.text.PTexts;
import com.prog.utils.*;
import com.prog.world.OreGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.nbt.NbtByte;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.projectile_damage.internal.Constants;
import net.purejosh.froglegs.init.FroglegsModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static com.prog.entity.attribute.PEntityAttributes.IMMUNITY_MAP;

public class Prog implements ModInitializer {
    public static final String MOD_ID = "prog";
    public static final String VERSION = "1.0.0";
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

        // Events
        //ServerTickEvents.START_WORLD_TICK.register(server -> LOGGER.info("WORLD"));
        TagEvents.TAG_LOADED.register((tagId, entries) -> {
            if (XCompat.isModLoaded(XIDs.FROG_LEGS)) {
                if (tagId.equals(PItemTags.GOURMET_FOOD.id())) {
                    @SuppressWarnings("unchecked")
                    Collection<RegistryEntry<Item>> itemEntries = (Collection<RegistryEntry<Item>>) entries;
                    itemEntries.add(FroglegsModItems.COOKED_FROG_LEG.getRegistryEntry());
                }
            }
        });

        RecipeEvents.RECIPES_LOADED.register(map -> {
            // Minecraft modding really does not seem to be made for compat.
//            if (XCompat.isModLoaded(XIDs.SUPPLEMENTARIES)) {
//                Supplier<?> wrapper = ModRegistry.BOMB_BLUE_ITEM;
//                var upgrade = Upgrades.register((Item)(Object)wrapper.get(), UEffectMapper.damage());
//                PRecipeProvider.getUpgradeRecipes(upgrade).forEach(builder -> builder.offer(provider -> map.put(builder.getId(), provider.toJson())));
//            }
        });

        EntityEvents.LIVING_ENTITY_TICK.register(entity -> {
            entity.stepHeight = (float) entity.getAttributeValue(PEntityAttributes.STEP_HEIGHT);
        });

        EntityEvents.PLAYER_ENTITY_TICK.register(player -> {
            PComponents.PLAYER.get(player).updateFlight();
            JetpackUtils.tickJetpack(player);
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

            if (item instanceof TridentItem) {
                double damage = TridentUtils.BASE_RANGED_DAMAGE;
                if (item instanceof TieredTridentItem tieredTridentItem) {
                    damage += tieredTridentItem.material.getDamageBonus();
                    damage += (double) EnchantmentUtils.getAttackDamageIncrease(EntityGroup.DEFAULT, stack, damage);
                }
                attributeModifiers.put(XEntityAttributes.PROJECTILE_DAMAGE, new EntityAttributeModifier(Constants.GENERIC_PROJECTILE_MODIFIER_ID, "Projectile modifier", damage, EntityAttributeModifier.Operation.ADDITION));
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