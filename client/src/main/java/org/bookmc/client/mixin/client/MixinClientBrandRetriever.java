package org.bookmc.client.mixin.client;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {
    /**
     * @author ChachyDev/BookMC
     */
    @Overwrite
    public static String getClientModName() {
        return "BookMC"; // We don't do no "vanilla" stuff here B)
    }
}
