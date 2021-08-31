package org.bookmc.mixin.server.internal.launch;

import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.bookmc.common.MinecraftCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftDedicatedServer.class)
public class MixinMinecraftDedicatedServer {
    @Inject(
        method = "setupServer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/dedicated/MinecraftDedicatedServer;setPlayerManager(Lnet/minecraft/server/PlayerManager;)V",
            ordinal = 0
        )
    )
    private void setupServer(CallbackInfoReturnable<Boolean> callbackInfo) {
        MinecraftCommon.INSTANCE = new MinecraftCommon.None();
        MinecraftCommon.INSTANCE.load();
    }
}
