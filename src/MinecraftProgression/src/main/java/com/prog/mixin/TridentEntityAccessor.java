package com.prog.mixin;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    // Accessor for the LOYALTY TrackedData
    @Accessor("LOYALTY")
    TrackedData<Byte> getLoyalty();

    // Accessor for dealtDamage
    @Accessor("dealtDamage")
    boolean getDealtDamage();

    @Accessor("dealtDamage")
    void setDealtDamage(boolean value);
}
