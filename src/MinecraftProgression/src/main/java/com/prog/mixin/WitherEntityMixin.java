package com.prog.mixin;

import com.prog.utils.WitherUtils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherEntity.class)
public class WitherEntityMixin {
    @Unique
    private final WitherEntity self = (WitherEntity) (Object) this;

    @Redirect(method = "createWitherAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 0))
    private static DefaultAttributeContainer.Builder changeHealth(DefaultAttributeContainer.Builder instance, EntityAttribute attribute, double baseValue) {
        return instance.add(EntityAttributes.GENERIC_MAX_HEALTH, WitherUtils.WITHER_MAX_HEALTH).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, WitherUtils.WITHER_ATTACK_DAMAGE);
    }

    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void injectDropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
        var stars = WitherUtils.getAdditionalStarDropAmount(self);
        for (var i = 0; i < stars; i++) {
            ItemEntity itemEntity = self.dropItem(Items.NETHER_STAR);
            if (itemEntity != null) {
                itemEntity.setCovetedItem();
            }
        }
    }
}
