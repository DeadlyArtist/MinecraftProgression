package com.prog.entity;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.nbt.NbtCompound;

public class DragonFireballEntityComponent implements Component, AutoSyncedComponent {
    public final DragonFireballEntity entity;
    public double sizeMultiplier = 1;

    public DragonFireballEntityComponent(DragonFireballEntity entity) {
        this.entity = entity;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        sizeMultiplier = nbt.getDouble("sizeMultiplier");
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putDouble("sizeMultiplier", sizeMultiplier);
    }
}
