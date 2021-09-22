package org.bookmc.internal.util;

public class Launcher {
    public static boolean isDevelopment() {
        return System.getenv("PG_IS_SERVER") != null;
    }
}
