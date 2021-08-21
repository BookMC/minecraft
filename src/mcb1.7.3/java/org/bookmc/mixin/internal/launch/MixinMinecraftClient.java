package org.bookmc.mixin.internal.launch;

import net.minecraft.client.MinecraftClient;
import org.bookmc.client.BookGameClient;
import org.bookmc.common.MinecraftCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(
        method = "launch",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;pushPhase(Ljava/lang/String;)V",
            args = "ldc=Startup",
            shift = At.Shift.AFTER,
            ordinal = 0
        )
    )
    private void startGame(CallbackInfo ci) {
        MinecraftCommon.INSTANCE = new BookGameClient();
        MinecraftCommon.INSTANCE.load();
    }
}
