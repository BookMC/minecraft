package org.bookmc.mixin.server.launch;

import net.minecraft.server.dedicated.DedicatedServer;
import org.bookmc.common.MinecraftCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class MixinDedicatedServer {
    @Inject(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/dedicated/DedicatedServer;setPlayerList(Lnet/minecraft/server/management/PlayerList;)V",
            ordinal = 0
        )
    )
    private void init(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        MinecraftCommon.INSTANCE = new MinecraftCommon.None();
        MinecraftCommon.INSTANCE.load();
    }
}