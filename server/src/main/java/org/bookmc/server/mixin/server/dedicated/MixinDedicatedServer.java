package org.bookmc.server.mixin.server.dedicated;

import net.minecraft.server.dedicated.DedicatedServer;
import org.bookmc.server.BookMinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class MixinDedicatedServer {
    @Inject(method = "startServer", at = @At(value = "RETURN"))
    private void startServer(CallbackInfoReturnable<Boolean> cir) {
        BookMinecraftServer.INSTANCE.load();
    }
}
