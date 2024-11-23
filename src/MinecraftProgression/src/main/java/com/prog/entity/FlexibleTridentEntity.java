package com.prog.entity;

import com.prog.utils.LOGGER;
import com.prog.utils.RangedUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;

public class FlexibleTridentEntity extends TridentEntity {
    protected EntityType<FlexibleTridentEntity> entityType;
    public FlexibleTridentEntity(EntityType<FlexibleTridentEntity> entityType, World world) {
        super(entityType, world);
        this.entityType = entityType;
    }

    public FlexibleTridentEntity(EntityType<FlexibleTridentEntity> entityType, World world, LivingEntity owner, ItemStack stack) {
        this(entityType, world);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ());
        this.tridentStack = stack.copy();
        this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
        this.setOwner(owner);
        if (owner instanceof PlayerEntity) {
            this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        }
        setDamage(RangedUtils.getBaseProjectileDamage(owner, stack));
    }

    public EntityType<FlexibleTridentEntity> getEntityType() {
        return entityType;
    }
}
