package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.NumberUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Redirect(method = "getName", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;append(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;"))
    public MutableText redirectGetLevel(MutableText instance, Text text, @Local int level) {
        return instance.append(Text.literal(NumberUtils.toRoman(level)));
    }

    @Inject(method = "getName", at = @At("TAIL"))
    public void setEnchantmentColor(int level, CallbackInfoReturnable<Text> cir, @Local MutableText mutableText) {
        if (level >= 30) {
            mutableText.formatted(Formatting.DARK_RED);
        } else if (level >= 20) {
            mutableText.formatted(Formatting.DARK_PURPLE);
        } else if (level >= 10) {
            mutableText.formatted(Formatting.LIGHT_PURPLE);
        }
    }
}
