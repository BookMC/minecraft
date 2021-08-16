package org.bookmc.mixin.branding;

import net.minecraft.obfuscate.DontObfuscate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "net.minecraft.client.ClientBrandRetriever")
public class MixinClientBrandRetriever {
    /**
     * @author ChachyDev
     */
    @DontObfuscate
    @Overwrite
    public static String getClientModName() {
        return "BookMC";
    }
}
