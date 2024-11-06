package com.prog.mixin;

import com.prog.Prog;
import com.prog.utils.TitleUtils;
import net.minecraft.GameVersion;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.ModStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Redirect(
            method = "getLeftText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/GameVersion;getName()Ljava/lang/String;",
                    ordinal = 0
            )
    )
    private String redirectGetGameVersion(GameVersion instance) {
       return TitleUtils.getVersionAppendum(instance);
    }

    @Redirect(
            method = "getLeftText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/GameVersion;getName()Ljava/lang/String;",
                    ordinal = 1
            )
    )
    private String redirectGetGameVersion2(GameVersion instance) {
        return TitleUtils.getVersionAppendum(instance);
    }
}
