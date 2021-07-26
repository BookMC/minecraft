package org.bookmc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.loader.api.vessel.ModVessel;
import org.bookmc.loader.impl.BookModLoader;
import org.bookmc.loader.impl.Loader;

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

        int modCount = Loader.getModVessels().size();

        if (modCount == 0) {
            logger.info("Starting BookMC with {} mod(s).", modCount);
        } else {
            logger.info("Starting BookMC with {} mod(s). Mods: {}", Loader.getModVessels().size(), mods.toString());
        }
        BookModLoader.load();
    }
}
