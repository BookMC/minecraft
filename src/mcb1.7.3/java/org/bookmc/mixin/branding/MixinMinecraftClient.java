package org.bookmc.mixin.branding;

import net.minecraft.client.MinecraftClient;
import org.bookmc.internal.util.constants.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @ModifyConstant(
        method = "startGame(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
        constant = @Constant(stringValue = "Minecraft")
    )
    private static String startGame(String s) {
        return "Minecraft Beta 1.7.3 (" + Constants.GAME_BRANDING + ")";
    }
}
