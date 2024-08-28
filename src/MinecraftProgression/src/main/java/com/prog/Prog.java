package com.prog;

import com.prog.enchantment.PEnchantments;
import com.prog.entity.PComponents;
import com.prog.entity.PStatusEffects;
import com.prog.entity.attribute.PDefaultAttributes;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.event.EntityEvents;
import com.prog.event.ItemStackEvents;
import com.prog.itemOrBlock.*;
import com.prog.itemOrBlock.custom.TieredTridentItem;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.text.PTexts;
import com.prog.utils.EntityAttributeModifierUtils;
import com.prog.utils.SlotUtils;
import com.prog.utils.TridentUtils;
import com.prog.utils.UpgradeUtils;
import com.prog.world.OreGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TridentItem;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.nbt.NbtByte;
import net.projectile_damage.internal.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.prog.entity.attribute.PEntityAttributes.IMMUNITY_MAP;

public class Prog implements ModInitializer {
    public static final String MOD_ID = "prog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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

        // Events
        //ServerTickEvents.START_WORLD_TICK.register(server -> LOGGER.info("WORLD"));
        EntityEvents.LIVING_ENTITY_TICK.register(entity -> {
            entity.stepHeight = (float) entity.getAttributeValue(PEntityAttributes.STEP_HEIGHT);
        });

        EntityEvents.PLAYER_ENTITY_TICK.register(player -> {
            PComponents.PLAYER.get(player).updateFlight();
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
                if (item instanceof TieredTridentItem tieredTridentItem)
                    damage += tieredTridentItem.material.getDamageBonus() + (double) EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);
                if (item instanceof TieredTridentItem tieredTridentItem) damage += tieredTridentItem.material.getDamageBonus();
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