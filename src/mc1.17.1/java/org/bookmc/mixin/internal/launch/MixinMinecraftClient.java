package org.bookmc.mixin.internal.launch;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.bookmc.client.BookGameClient;
import org.bookmc.common.MinecraftCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    /**
     * Inject into the constructor of {@link net.minecraft.client.MinecraftClient} and
     * begin to load the independent loader.
     *
     * @param runArgs The arguments passed to the game via launch
     * @param ci      See {@link CallbackInfo}
     */
    @Inject(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/util/Window;setPhase(Ljava/lang/String;)V",
            args = "ldc=Startup",
            shift = At.Shift.AFTER,
            ordinal = 0
        )
    )
    private void init(RunArgs runArgs, CallbackInfo ci) {
        MinecraftCommon.INSTANCE = new BookGameClient();
        MinecraftCommon.INSTANCE.load();
    }
}
