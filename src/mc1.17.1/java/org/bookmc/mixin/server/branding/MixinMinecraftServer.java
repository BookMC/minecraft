package org.bookmc.mixin.server.branding;

import net.minecraft.server.MinecraftServer;
import org.bookmc.constants.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Overwrite
    public String getServerModName() {
        return Constants.GAME_BRANDING;
    }
}
