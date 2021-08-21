package org.bookmc.client.mixin.branding;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {
    @ModifyConstant(method = "drawScreen", constant = @Constant(stringValue = "Minecraft 1.9.4"))
    private String getVersionString(String s) {
        return s + " (BookMC)";
    }
}