package org.bookmc.client.mixin.client;

import net.minecraft.client.Minecraft;
import org.bookmc.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "startGame", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", args = "ldc=Pre startup", shift = At.Shift.AFTER))
    private void startGameHead(CallbackInfo ci) {
        MinecraftClient.INSTANCE.preload();
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", args = "ldc=Startup", shift = At.Shift.AFTER))
    private void startGame(CallbackInfo ci) {
        MinecraftClient.INSTANCE.load();
    }
}
