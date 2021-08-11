package org.bookmc.mixin.branding;

import net.minecraft.client.gui.GuiOverlayDebug;
import org.bookmc.loader.impl.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(GuiOverlayDebug.class)
public class MixinGuiOverlayDebug {
    @ModifyVariable(method = "getDebugInfoRight", ordinal = 0, at = @At("RETURN"))
    private List<String> getDebugInfoRight(List<String> list) {
        list.add("");
        list.add("Mods loaded: " + Loader.getModVessels().size());
        return list;
    }
}
