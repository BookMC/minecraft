package org.bookmc.mixin.client.launch;

import org.bookmc.client.BookGameClient;
import net.minecraft.client.Minecraft;
import org.bookmc.common.MinecraftCommon;
import org.bookmc.version.MinecraftVersionLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(
        method = "startGame",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V",
            args = "ldc=Startup",
            shift = At.Shift.AFTER,
            ordinal = 0
        )
    )
    private void startGame(CallbackInfo ci) {
        MinecraftCommon.INSTANCE = new BookGameClient();
        MinecraftCommon.INSTANCE.load();
    }

    /**
     * @author ChachyDev
     */
    @Redirect(method = "getVersion", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;launchedVersion:Ljava/lang/String;"))
    private String getVersion(Minecraft minecraft) {
        return MinecraftVersionLookup.find().id();
    }
}
