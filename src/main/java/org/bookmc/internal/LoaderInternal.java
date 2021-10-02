package org.bookmc.internal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.loader.api.loader.BookLoaderBase;

public class LoaderInternal {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void load() {
        LOGGER.info("Intialising mod loading (book-loader)");
        BookLoaderBase.INSTANCE.load();
    }
}