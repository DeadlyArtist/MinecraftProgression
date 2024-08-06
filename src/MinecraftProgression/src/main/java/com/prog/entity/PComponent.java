package com.prog.entity;

import com.prog.Prog;
import com.prog.itemOrBlock.GourmetFoods;
import com.prog.itemOrBlock.PItemTags;
import com.prog.utils.EntityAttributeModifierUtils;
import com.prog.utils.ItemUtils;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PComponent implements Component, ServerTickingComponent, AutoSyncedComponent {
    public final LivingEntity entity;
    public Set<String> eatenGourmetFoods = new HashSet<>();

    public PComponent(LivingEntity entity) {
        this.entity = entity;
    }

    public boolean eat(Item item) {
        if (!ItemUtils.hasTag(item, PItemTags.GOURMET_FOOD) || hasEaten(item)) return false;

        eatenGourmetFoods.add(Registry.ITEM.getId(item).toString());
        var effects = GourmetFoods.data.get(item).effects;
        effects.forEach(effect -> entity.getAttributeInstance(effect.target).addPersistentModifier(effect.modifier));

        PComponents.COMPONENT.sync(entity);
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
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        var foodsNbt = new NbtList();
        eatenGourmetFoods.forEach(f -> foodsNbt.add(NbtString.of(f)));
        nbt.put("eaten_gourmet_foods", foodsNbt);
    }
}
