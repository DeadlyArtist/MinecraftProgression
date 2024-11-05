package com.prog.entity;

import com.prog.itemOrBlock.GourmetFoods;
import com.prog.itemOrBlock.PItemTags;
import com.prog.utils.GourmetUtils;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LivingEntityComponent implements Component, ServerTickingComponent, AutoSyncedComponent {
    public final LivingEntity entity;
    public Set<String> eatenGourmetFoods = new HashSet<>();

    public LivingEntityComponent(LivingEntity entity) {
        this.entity = entity;
    }

    public void updateGourmetFoodEffect(Item item) {
        // Remove existing effects
        var attributes = entity.getAttributes();
        Set<String> existingEffects = new HashSet<>();
        for (var attributeInstance : attributes.custom.values()) {
            attributeInstance.getModifiers().forEach(mod -> {
                if (mod.getName().startsWith(GourmetUtils.getGourmetModifierNamePrefix(item))) {
                    attributeInstance.removeModifier(mod);
                    existingEffects.add(mod.getName());
                }
            });
        }

        // Add effects
        if (!ItemUtils.hasTag(item, PItemTags.GOURMET_FOOD)) return;
        var gourmetFoodData = GourmetFoods.data.get(item);
        if (gourmetFoodData == null) return;
        var effects = gourmetFoodData.effects;
        for (int index = 0; index < effects.size(); index++) {
            var effect = effects.get(index);
            var attributeInstance = attributes.getCustomInstance(effect.target);
            var name = GourmetUtils.getGourmetModifierName(item, index);
            var modifier = effect.copyWithName(name).modifier;
            attributeInstance.addPersistentModifier(modifier);
            if (effect.target == EntityAttributes.GENERIC_MAX_HEALTH && effect.modifier.getOperation() == EntityAttributeModifier.Operation.ADDITION && !existingEffects.contains(name)) {
                entity.heal((float) effect.modifier.getValue());
            }
        }
    }

    public void updateAllGourmetFoodEffects() {
        eatenGourmetFoods.forEach(foodId -> updateGourmetFoodEffect(ItemUtils.byId(foodId)));
    }

    public boolean eat(Item item) {
        if (!ItemUtils.hasTag(item, PItemTags.GOURMET_FOOD) || hasEaten(item)) return false;

        eatenGourmetFoods.add(Registry.ITEM.getId(item).toString());
        updateGourmetFoodEffect(item);

        PComponents.LIVING_ENTITY.sync(entity);
        return true;
    }

    public Set<Item> getEatenGourmetItems() {
        return new HashSet<>(eatenGourmetFoods.stream().map(id -> ItemUtils.byId(id)).toList());
    }

    public boolean hasEaten(Item item){
        return eatenGourmetFoods.contains(ItemUtils.getId(item).toString());
    }

    @Override
    public void serverTick() {

    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        eatenGourmetFoods = new HashSet<>(nbt.getList("eaten_gourmet_foods", NbtElement.STRING_TYPE).stream().map(e -> ((NbtString) e).asString()).toList());
        updateAllGourmetFoodEffects();
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        var foodsNbt = new NbtList();
        eatenGourmetFoods.forEach(f -> foodsNbt.add(NbtString.of(f)));
        nbt.put("eaten_gourmet_foods", foodsNbt);
    }
}
