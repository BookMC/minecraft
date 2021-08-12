package org.bookmc.client;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.common.MinecraftCommon;
import org.lwjgl.opengl.Display;

public class MinecraftClient extends MinecraftCommon {
    private final Logger logger = LogManager.getLogger();

    public static MinecraftClient INSTANCE = new MinecraftClient();

    public void preload() {
        logger.info("Preloading BookMC!");
    }

    @Override
    public void load() {
        super.load();
        Display.setTitle(String.format("Minecraft %s (BookMC)", Minecraft.getMinecraft().getVersion()));
    }
}
