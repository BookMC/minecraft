package org.bookmc.mixin.branding;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @ModifyVariable(
        method = "getWindowTitle",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;",
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/MinecraftClient;isModded()Z"
            )
        )
    )
    private StringBuilder getWindowTitle(StringBuilder builder) {
        builder.append("/BookMC");
        return builder;
    }
}
