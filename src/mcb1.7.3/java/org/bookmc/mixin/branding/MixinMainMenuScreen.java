package org.bookmc.mixin.branding;

import net.minecraft.client.screen.menu.MainMenuScreen;
import org.bookmc.internal.util.constants.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MainMenuScreen.class)
public class MixinMainMenuScreen {
    @ModifyConstant(
        method = "render",
        constant = @Constant(stringValue = "Minecraft Beta 1.7.3")
    )
    private String getGameString(String s) {
        return s + " (" + Constants.GAME_BRANDING + ")";
    }
}
