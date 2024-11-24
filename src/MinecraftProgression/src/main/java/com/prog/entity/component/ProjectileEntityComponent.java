package com.prog.entity.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ProjectileEntityComponent implements Component, AutoSyncedComponent {
    public final ProjectileEntity entity;
    protected ItemStack sourceStack;
    public double originalPlayerY;

    public ProjectileEntityComponent(ProjectileEntity entity) {
        this.entity = entity;
    }

    public ItemStack getSourceStack() {
        return sourceStack;
    }

    public void setSourceStack(ItemStack stack) {
        sourceStack = stack.copy();
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        sourceStack = ItemStack.fromNbt(nbt.getCompound("sourceStack"));
        if (sourceStack.isEmpty()) sourceStack = null;
        originalPlayerY = nbt.getDouble("originalPlayerHeight");
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        if (sourceStack != null) nbt.put("sourceStack", sourceStack.getNbt());
        nbt.putDouble("originalPlayerHeight", originalPlayerY);
    }
}