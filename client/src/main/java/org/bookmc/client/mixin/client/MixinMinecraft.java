package org.bookmc.client.mixin.client;

import net.minecraft.client.Minecraft;
import org.bookmc.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGameHead(CallbackInfo ci) {
        MinecraftClient.INSTANCE.preload();
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void startGameReturn(CallbackInfo ci) {
        MinecraftClient.INSTANCE.load();
    }
}
