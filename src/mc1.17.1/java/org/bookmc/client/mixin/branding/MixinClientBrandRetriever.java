package org.bookmc.client.mixin.branding;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.obfuscate.DontObfuscate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
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
