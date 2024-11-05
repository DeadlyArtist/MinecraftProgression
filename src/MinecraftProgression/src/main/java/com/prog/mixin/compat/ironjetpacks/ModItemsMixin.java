package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.config.ModJetpacks;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.item.ModItems;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import com.google.common.base.Suppliers;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(value = ModItems.class, remap = false)
public abstract class ModItemsMixin {

    @Inject(method = "register(Ljava/lang/String;Ljava/util/function/Supplier;)Ljava/util/function/Supplier;", at = @At("HEAD"), cancellable = true)
    private static void injectRegister(String name, Supplier<Item> item, CallbackInfoReturnable<Supplier<Item>> cir) {
        if (name.equals("strap") || name.equals("basic_coil") || name.equals("advanced_coil") ||
                name.equals("elite_coil") || name.equals("ultimate_coil") || name.equals("expert_coil")) {
            cir.setReturnValue(item); // Return the item immediately, skipping adding it to the registry
        }
    }

    @Inject(method = "register()V", at = @At("HEAD"), cancellable = true)
    private static void onRegister(CallbackInfo ci) {
        Registry<Item> registry = Registry.ITEM;
        JetpackRegistry jetpacks = JetpackRegistry.getInstance();

        ModItems.ENTRIES.forEach((id, itemx) -> {
            Registry.register(registry, id, itemx.get());
        });

        ModJetpacks.loadJsons();

        for (var jetpack : jetpacks.getAllJetpacks()) {
            Registry.register(registry, new Identifier("iron-jetpacks", jetpack.name + "_jetpack"), (JetpackItem) jetpack.item.get());
        }

        ci.cancel(); // Overwrite the method
    }
}
