package com.prog.mixin;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.ExperienceOrbSpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrbSpawnS2CPacket.class)
public class ExperienceOrbSpawnS2CPacketMixin {

    @Unique
    private final ExperienceOrbSpawnS2CPacket self = (ExperienceOrbSpawnS2CPacket) (Object) this;

    @Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("TAIL"))
    private void redirectReadValue(PacketByteBuf buf, CallbackInfo ci) {
        self.experience = buf.readVarInt();
    }

    @Inject(method = "write", at = @At("TAIL"))
    private void redirectWriteValue(PacketByteBuf buf, CallbackInfo ci) {
        buf.writeVarInt(self.experience);
    }
}
