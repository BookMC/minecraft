package org.bookmc.mixin.branding;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {
    /**
     * @author ChachyDev/BookMC
     * @reason We aren't vanilla.
     */
    @Overwrite
    public static String getClientModName() {
        return "BookMC"; // We don't do no "vanilla" stuff here B)
    }
}
