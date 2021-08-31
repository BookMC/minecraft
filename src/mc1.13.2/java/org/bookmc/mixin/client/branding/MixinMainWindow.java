package org.bookmc.mixin.client.branding;

import net.minecraft.client.MainWindow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

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
