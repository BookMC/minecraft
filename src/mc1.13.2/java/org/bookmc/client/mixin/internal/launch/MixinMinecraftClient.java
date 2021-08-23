package org.bookmc.client.mixin.internal.launch;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import org.bookmc.client.BookGameClient;
import org.bookmc.common.MinecraftCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraftClient {
    /**
     * Inject into the constructor of {@link net.minecraft.client.Minecraft} and
     * begin to load the independent loader.
     *
     * @param config The arguments passed to the game via launch
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
    private void init(GameConfiguration config, CallbackInfo ci) {
        MinecraftCommon.INSTANCE = new BookGameClient();
        MinecraftCommon.INSTANCE.load();
    }
}