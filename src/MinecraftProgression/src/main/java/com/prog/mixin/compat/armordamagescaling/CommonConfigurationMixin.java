package com.prog.mixin.compat.armordamagescaling;

import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.armordamagescale.config.CommonConfiguration;

@Mixin(value = CommonConfiguration.class, remap = false)
public abstract class CommonConfigurationMixin {

    @Inject(method = "serialize", at = @At("HEAD"))
    public void serialize(CallbackInfoReturnable<JsonObject> cir) {
        CommonConfiguration self = (CommonConfiguration) (Object) this;
        self.playerdamageFormula = "damage";

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // damage = 1
        // armor = 10
        // offset = 25
        // formula1 = damage * offset / (offset + armor)
        // formula2 = damage * 1 / (1 + armor / 10);
        // [formula1, formula2].join("    ")
        var offset = 25;
        self.armorFormula = "damage*(" + offset + "/(armor+" + offset + "))";

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // damage = 1
        // toughness = 10
        // hitpct = 0.25
        // formula1 = damage * (1 / (toughness / 10 + 1) * hitpct + (1 - hitpct));
        // [formula1, formula2].join("    ")
        // Armor toughness reduces damage in relation to percent health lost.Input values:hitpct(0-1), toughness, damage.
        //self.toughnessFormula = "damage*(1/(toughness/10+1)*hitpct+(1-hitpct))";
    }
}
