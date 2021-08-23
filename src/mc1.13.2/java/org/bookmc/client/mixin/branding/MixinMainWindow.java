package org.bookmc.client.mixin.branding;

import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.VirtualScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MainWindow.class)
public class MixinMainWindow {
    @ModifyConstant(
        method = "<init>",
        constant = @Constant(stringValue = "Minecraft 1.13.2")
    )
    private String getWindowTitle(String s) {
        return s.concat(" (BookMC)");
    }
}
