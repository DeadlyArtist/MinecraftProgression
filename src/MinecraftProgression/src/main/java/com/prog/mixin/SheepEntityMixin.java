package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.itemOrBlock.custom.TieredShearsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    @Unique
    @Final
    private final SheepEntity self = (SheepEntity) (Object) this;

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return instance.getItem() instanceof ShearsItem;
    }

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;sheared(Lnet/minecraft/sound/SoundCategory;)V"))
    public void redirectSheared(SheepEntity instance, SoundCategory shearedSoundCategory, @Local ItemStack itemStack) {
        sheared(shearedSoundCategory, itemStack.getItem());
    }

    @Unique
    private void sheared(SoundCategory shearedSoundCategory, Item shearsItem) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        self.setSheared(true);
        var woolBonus = 0;
        if (shearsItem instanceof TieredShearsItem tiered) woolBonus = tiered.material.getWoolBonus();
        int i = 1 + this.random.nextInt(3);

        for (int j = 0; j < i; j++) {
            ItemEntity itemEntity = this.dropItem((ItemConvertible) SheepEntity.DROPS.get(self.getColor()), 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(
                        itemEntity.getVelocity()
                                .add(
                                        (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F),
                                        (double) (this.random.nextFloat() * 0.05F),
                                        (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)
                                )
                );
            }
        }
    }
}
