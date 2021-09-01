package org.bookmc.mixin.client.launch;

import net.minecraft.client.Minecraft;
import org.bookmc.client.BookGameClient;
import org.bookmc.common.MinecraftCommon;
import org.bookmc.version.MinecraftVersionLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
     */
    @ModifyVariable(method = "getVersion", at = @At("HEAD"), ordinal = 0)
    private String getVersion(String version) {
        return MinecraftVersionLookup.find().id();
    }
}
