package org.bookmc.mixin.client.launch;

import net.minecraft.client.Minecraft;
import org.bookmc.client.BookGameClient;
import org.bookmc.common.MinecraftCommon;
import org.bookmc.version.MinecraftVersionLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V",
            args = "ldc=Startup",
            shift = At.Shift.AFTER,
            ordinal = 0
        )
    )
    private void init(CallbackInfo ci) {
        MinecraftCommon.INSTANCE = new BookGameClient();
        MinecraftCommon.INSTANCE.load();
    }

    /**
     * @author ChachyDev
     * @reason Thank me later developers! Instead of returning the profile name we actually return useful data (the game version)
     */
    @Overwrite
    public String getVersion() {
        return MinecraftVersionLookup.find().id();
    }
}
