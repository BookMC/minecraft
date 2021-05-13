package org.bookmc.client;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.loader.BookModLoader;
import org.bookmc.loader.Loader;
import org.bookmc.loader.MinecraftModDiscoverer;
import org.bookmc.loader.book.DevelopmentModDiscoverer;
import org.bookmc.loader.vessel.ModVessel;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.util.List;

public class MinecraftClient {
    private final Logger logger = LogManager.getLogger();

    public static MinecraftClient INSTANCE = new MinecraftClient();

    private final File modsFolder = new File("mods");

    public void preload() {
        logger.info("Preloading BookMC!");
    }

    public void load() {
        StringBuilder mods = new StringBuilder();
        List<ModVessel> vesselList = Loader.getModVessels();
        for (ModVessel vessel : vesselList) {
            mods.append(vessel.getName());
            if (vesselList.indexOf(vessel) + 1 != vesselList.size()) {
                mods.append(", ");
            }
        }
        logger.info("Starting BookMC with {} mod(s). Mods: {}", Loader.getModVessels().size(), mods.toString());
        BookModLoader.load();
        Display.setTitle(String.format("Minecraft %s (BookMC)", Minecraft.getMinecraft().getVersion()));
    }

    public File getModsFolder() {
        return modsFolder;
    }
}
