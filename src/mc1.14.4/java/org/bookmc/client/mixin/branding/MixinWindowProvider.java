package org.bookmc.client.mixin.branding;

import net.minecraft.client.util.WindowProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WindowProvider.class)
public class MixinWindowProvider {
    @ModifyVariable(method = "createWindow", at = @At("HEAD"), ordinal = 1)
    private String getWindowTitle(String s) {
        return s.concat(" (BookMC)");
    }
}
