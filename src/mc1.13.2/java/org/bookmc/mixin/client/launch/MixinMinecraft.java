package org.bookmc.mixin.client.launch;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import org.bookmc.common.MinecraftCommon;
import org.bookmc.version.MinecraftVersionLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    /**
     * Inject into the constructor of {@link net.minecraft.client.Minecraft} and
     * begin to load the independent loader.
     *
     * @param config The arguments passed to the game via launch
     * @param ci     See {@link CallbackInfo}
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
        MinecraftCommon.INSTANCE = new MinecraftCommon.None();
        MinecraftCommon.INSTANCE.load();
    }

    /**
     * @author ChachyDev
     */
    @Overwrite
    public String getVersion() {
        return MinecraftVersionLookup.find().id();
    }
}
