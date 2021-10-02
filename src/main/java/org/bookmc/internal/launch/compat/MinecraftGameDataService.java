package org.bookmc.internal.launch.compat;

import org.bookmc.internal.util.version.MinecraftVersionLookup;
import org.bookmc.internal.util.version.api.asm.yarn.utils.YarnUtils;
import org.bookmc.loader.api.environment.GameEnvironment;
import org.bookmc.loader.api.loader.BookLoaderBase;
import org.bookmc.loader.api.service.GameDataService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class MinecraftGameDataService implements GameDataService {
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
        return BookLoaderBase.INSTANCE.getGlobalEnvironment() == GameEnvironment.CLIENT ? "net.minecraft.client.main.Main" : (YarnUtils.isYarnMapped() ? "net.minecraft.server.MinecraftServer" : "net.minecraft.server.Main");
    }

    @Override
    public Path getWorkingDirectory(Map<String, String> arguments) {
        return Paths.get(arguments.getOrDefault("gameDir", System.getProperty("user.dir")));
    }
}
