package com.prog.itemOrBlock.custom;

import com.prog.utils.FabricUtils;
import com.prog.utils.GlobalPosUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.StructureUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structures;

public class AncientCompassItem extends Item {
    public static String ANCIENT_CITY_KEY = "AncientCity";

    public AncientCompassItem(Settings settings) {
        super(settings);

        if (FabricUtils.isClient()) {
            registerModel();
        }
    }

    @Environment(EnvType.CLIENT)
    public void registerModel() {
        ModelPredicateProviderRegistry.register(this, new Identifier("angle"), new CompassAnglePredicateProvider(AncientCompassItem::getTargetPos));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (world.isClient) return;

        var nbt = stack.getOrCreateNbt();
        var nearestCity = StructureUtils.locate((ServerWorld) world, Structures.ANCIENT_CITY, entity.getBlockPos(), 100, false);
        if (nearestCity == null) {
            nbt.remove(ANCIENT_CITY_KEY);
        } else {
            var globalPos = GlobalPos.create(world.getRegistryKey(), nearestCity.getFirst());
            GlobalPosUtils.toNbt(nbt, ANCIENT_CITY_KEY, globalPos);
        }
    }

    public static GlobalPos getTargetPos(ClientWorld world, ItemStack stack, Entity entity) {
        var nbt = stack.getOrCreateNbt();
        return GlobalPosUtils.fromNbt(nbt, ANCIENT_CITY_KEY);
    }
}
