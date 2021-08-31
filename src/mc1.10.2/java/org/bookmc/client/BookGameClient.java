package org.bookmc.client;

import org.bookmc.common.MinecraftCommon;
import org.bookmc.constants.Constants;
import org.lwjgl.opengl.Display;

public class BookGameClient extends MinecraftCommon {
    @Override
    public void load() {
        Display.setTitle(Display.getTitle().concat(" ("  + Constants.GAME_BRANDING + ")"));
        super.load();
    }
}
