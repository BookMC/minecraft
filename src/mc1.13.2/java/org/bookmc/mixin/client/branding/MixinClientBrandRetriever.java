package org.bookmc.mixin.client.branding;

import net.minecraft.client.ClientBrandRetriever;
import org.bookmc.constants.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {
    /**
     * @author ChachyDev
     */
    @Overwrite
    public static String getClientModName() {
        return Constants.GAME_BRANDING;
    }
}
