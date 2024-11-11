package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.IronJetpacks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IronJetpacks.class)
public class IronJetpacksMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/itemgroup/FabricItemGroupBuilder;create(Lnet/minecraft/util/Identifier;)Lnet/fabricmc/fabric/api/client/itemgroup/FabricItemGroupBuilder;"))
    private static FabricItemGroupBuilder redirectCreateGroup(Identifier identifier) {
        return FabricItemGroupBuilder.create(new Identifier("iron-jetpacks", "iron-jetpacks")).icon(() -> {
            return null;
        });
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/itemgroup/FabricItemGroupBuilder;build()Lnet/minecraft/item/ItemGroup;"))
    private static ItemGroup redirectCreateGroup(FabricItemGroupBuilder instance) {
        return null;
    }
}
