package org.bookmc.client;

import org.bookmc.common.MinecraftCommon;
import org.lwjgl.opengl.Display;

public class BookGameClient extends MinecraftCommon {
    @Override
    public void load() {
        Display.setTitle(Display.getTitle().concat(" (BookMC)"));
        super.load();
    }
}
