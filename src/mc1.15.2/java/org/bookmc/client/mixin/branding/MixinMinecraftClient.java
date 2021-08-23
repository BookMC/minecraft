package org.bookmc.client.mixin.branding;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.WindowProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @ModifyVariable(
        method = "getWindowTitle",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;",
            ordinal = 0,
            shift = At.Shift.BEFORE
        )
    )
    private StringBuilder getWindowTitle(StringBuilder builder) {
        builder.append(" (BookMC)");
        return builder;
    }
}
