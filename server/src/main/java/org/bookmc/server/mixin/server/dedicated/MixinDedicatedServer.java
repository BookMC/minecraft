package org.bookmc.server.mixin.server.dedicated;

import net.minecraft.server.dedicated.DedicatedServer;
import org.bookmc.server.BookMinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public abstract class MixinDedicatedServer {
    @Shadow public abstract boolean isDedicatedServer();

    @Inject(method = "startServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/DedicatedServer;setConfigManager(Lnet/minecraft/server/management/ServerConfigurationManager;)V"))
    private void startServer(CallbackInfoReturnable<Boolean> cir) {
        if (isDedicatedServer()) {
            BookMinecraftServer.INSTANCE.load();
        }
    }
}
