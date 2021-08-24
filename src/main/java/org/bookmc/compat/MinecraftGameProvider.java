package org.bookmc.compat;

import org.bookmc.loader.impl.launch.provider.ArgumentHandler;
import org.bookmc.loader.impl.launch.provider.GameProvider;
import org.bookmc.version.MinecraftVersionLookup;

import java.io.File;

public class MinecraftGameProvider implements GameProvider {
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
        return "net.minecraft.client.main.Main";
    }
}
