package org.bookmc.client;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.loader.BookModLoader;
import org.bookmc.loader.Loader;
import org.bookmc.loader.MinecraftModDiscoverer;
import org.bookmc.loader.book.DevelopmentModDiscoverer;
import org.lwjgl.opengl.Display;

import java.io.File;

public class MinecraftClient {
    private final Logger logger = LogManager.getLogger();

    public static MinecraftClient INSTANCE = new MinecraftClient();

    private final File modsFolder = new File("mods");

    public void preload() {
        logger.info("Preloading BookMC!");
        // Discover mods at preload
        for (MinecraftModDiscoverer loader : Loader.getModDiscoverers()) {
            File[] mods = modsFolder.listFiles();
            if (mods != null || loader instanceof DevelopmentModDiscoverer) {
                loader.discover(mods);
            }
        }
    }

    public void load() {
        logger.info("Starting BookMC with {} mods", Loader.getModVessels().size());
        BookModLoader.load();
        Display.setTitle(String.format("Minecraft %s (BookMC)", Minecraft.getMinecraft().getVersion()));
    }

    public File getModsFolder() {
        return modsFolder;
    }
}
