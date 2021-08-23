package org.bookmc.client.mixin.branding;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {
    @ModifyConstant(method = "drawScreen", constant = @Constant(stringValue = "Minecraft 1.10.2"))
    private String getVersionString(String s) {
        return s + " (BookMC)";
    }
}
