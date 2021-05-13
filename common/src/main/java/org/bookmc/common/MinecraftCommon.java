package org.bookmc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.loader.BookModLoader;
import org.bookmc.loader.Loader;
import org.bookmc.loader.vessel.ModVessel;

import java.util.List;

public abstract class MinecraftCommon {
    private final Logger logger = LogManager.getLogger();

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
    }
}
