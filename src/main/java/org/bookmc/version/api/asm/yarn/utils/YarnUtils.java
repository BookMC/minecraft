package org.bookmc.version.api.asm.yarn.utils;

import java.io.IOException;
import java.io.InputStream;

public class YarnUtils {
    public static byte[] getVersionLookupClass() {
        try (InputStream stream = YarnUtils.class.getResourceAsStream(toResourceLocation("net.minecraft.MinecraftVersion"))) {
            if (stream != null) {
                return stream.readAllBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static String toResourceLocation(String name) {
        return "/" + name.replace(".", "/").concat(".class");
    }
}
