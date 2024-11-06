package com.prog.mixin;

import com.prog.utils.TitleUtils;
import net.minecraft.GameVersion;
import net.minecraft.util.ModStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/GameVersion;getName()Ljava/lang/String;"
            )
    )
    private String redirectGetGameVersion(GameVersion instance) {
        return TitleUtils.getVersionAppendum(instance);
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/ModStatus;isModded()Z"
            )
    )
    private boolean redirectIsModded(ModStatus instance) {
        return false;
    }
}
