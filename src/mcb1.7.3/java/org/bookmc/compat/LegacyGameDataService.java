package org.bookmc.compat;

import org.bookmc.internal.util.version.MinecraftVersionLookup;
import org.bookmc.loader.api.service.GameDataService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class LegacyGameDataService implements GameDataService {
    @Override
    public String getGameName() {
        return "Minecraft";
    }

    @Override
    public String getGameVersion() {
        return MinecraftVersionLookup.find().id();
    }

    @Override
    public String getGameEntrypoint() {
        return "net.minecraft.client.MinecraftClient";
    }

    @Override
    public Path getWorkingDirectory(Map<String, String> map) {
        return Paths.get(map.getOrDefault("gameDir", System.getProperty("user.dir")));
    }
}
