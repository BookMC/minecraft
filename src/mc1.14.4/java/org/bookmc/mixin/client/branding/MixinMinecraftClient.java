package org.bookmc.mixin.client.branding;

import net.minecraft.client.MinecraftClient;
import org.bookmc.internal.util.constants.Constants;
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
        builder.append(" (" + Constants.GAME_BRANDING + ")");
        return builder;
    }
}
