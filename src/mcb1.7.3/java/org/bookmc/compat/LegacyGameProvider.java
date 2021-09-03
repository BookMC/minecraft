package org.bookmc.compat;

import org.bookmc.internal.util.version.MinecraftVersionLookup;
import org.bookmc.loader.impl.launch.provider.ArgumentHandler;
import org.bookmc.loader.impl.launch.provider.GameProvider;

import java.io.File;

public class LegacyGameProvider implements GameProvider {
    private ArgumentHandler handler;

    private String version;

    private File assetsDirectory;
    private File gameDirectory;


    @Override
    public void load(ArgumentHandler handler) {
        this.handler = handler;
    }

    @Override
    public String getLaunchedVersion() {
        if (version == null) {
            version = MinecraftVersionLookup.find().id();
        }

        return version;
    }

    @Override
    public File getAssetsDirectory() {
        if (assetsDirectory == null) {
            assetsDirectory = new File(handler.get("assetsDir").orElseThrow(() -> new IllegalStateException("The game was not loaded correctly, could not find assets directory")));
        }

        return assetsDirectory;
    }

    @Override
    public File getGameDirectory() {
        if (gameDirectory == null) {
            gameDirectory = new File(handler.get("gameDir").orElse("."));
        }

        return gameDirectory;
    }

    @Override
    public String getLaunchTarget() {
        // If you're not a client then there is a problem
        return "net.minecraft.client.MinecraftClient";
    }
}
