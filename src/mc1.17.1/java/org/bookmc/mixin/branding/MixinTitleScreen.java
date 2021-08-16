package org.bookmc.mixin.branding;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(targets = "net.minecraft.client.gui.screen.TitleScreen")
public class MixinTitleScreen {
    @ModifyVariable(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;isDemo()Z",
            shift = At.Shift.BEFORE
        ),
        ordinal = 0
    )
    private String getGameString(String game) {
        return game + " (BookMC)";
    }
}
