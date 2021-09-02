package org.bookmc.mixin.client.branding;

import net.minecraft.client.gui.GuiMainMenu;
import org.bookmc.constants.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {
    @ModifyConstant(method = "drawScreen", constant = @Constant(stringValue = "Minecraft 1.8.9"))
    private String getVersionString(String s) {
        return s + " (" + Constants.GAME_BRANDING + ")";
    }
}
