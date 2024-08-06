package com.prog;

import com.prog.enchantment.PEnchantments;
import com.prog.entity.PComponents;
import com.prog.entity.attribute.PDefaultAttributes;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.event.EntityEvents;
import com.prog.event.ItemStackEvents;
import com.prog.itemOrBlock.*;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.text.PTexts;
import com.prog.utils.EntityAttributeModifierUtils;
import com.prog.utils.ItemUtils;
import com.prog.utils.SlotUtils;
import com.prog.utils.UpgradeUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
        GourmetFoods.init();

        // Events
        //ServerTickEvents.START_WORLD_TICK.register(server -> LOGGER.info("WORLD"));
        EntityEvents.LIVING_ENTITY_TICK.register(entity -> {
            entity.stepHeight = (float) entity.getAttributeValue(PEntityAttributes.STEP_HEIGHT);
        });

        ItemStackEvents.ITEM_STACK_CTOR.register((stack) -> {
            if (stack.isIn(PItemTags.UPGRADABLE)) {
                stack.setSubNbt(ItemStack.UNBREAKABLE_KEY, NbtByte.ONE);
            }
        });

        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) -> {
            if (!SlotUtils.isEquipped(stack, slot)) return;

            var upgrades = UpgradeUtils.extractUpgradeData(stack);
            upgrades.forEach((name, effects) -> effects.forEach(effect -> attributeModifiers.put(effect.target, effect.modifier)));

//            Example
//            if (stack.isOf(Items.DIAMOND_HELMET) && slot.getEntitySlotId() == HEAD_SLOT_ID) {
//                attributeModifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, MODIFIER);
//            }
        });

        EntityEvents.APPLY_FOOD_EFFECTS.register((entity, stack) -> {
            PComponents.COMPONENT.get(entity).eat(stack.getItem());
        });

        EntityEvents.CAN_HAVE_STATUS_EFFECT.register((entity, effectInstance) -> {
            StatusEffect effect = effectInstance.getEffectType();
            var immunityAttribute = IMMUNITY_MAP.get(effect);
            if (immunityAttribute != null && entity.getAttributeValue(immunityAttribute) == 1.0) {
                return false;
            }

            return true;
        });
    }
}