package com.prog.mixin;

import com.prog.event.CommandEvents;
import com.prog.mixinInterfaces.IPersistentProjectileEntityMixin;
import net.minecraft.server.command.CommandManager;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.command.CommandRegistryAccess;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @Shadow @Final private static Logger LOGGER;
    @Unique
    final CommandManager self = (CommandManager) (Object) this;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectCustomCommand(CommandManager.RegistrationEnvironment environment, CommandRegistryAccess commandRegistryAccess, CallbackInfo ci) {
        CommandEvents.REGISTER_COMMANDS.invoker().register(self.dispatcher);
    }

    // Uncomment to debug commands
//    @Redirect(method = "execute", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
//    private boolean redirectIsDevelopment() {
//        return true;
//    }
}
