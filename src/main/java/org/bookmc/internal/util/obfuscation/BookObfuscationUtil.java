package org.bookmc.internal.util.obfuscation;

import com.google.gson.Gson;
import org.bookmc.internal.util.obfuscation.data.Mappings;
import org.bookmc.internal.util.ASMUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

public class BookObfuscationUtil {
    private static final Gson gson = new Gson();
    private static final Mappings mappings;

    static {
        String rawMappings = getMappings();
        if (rawMappings != null) {
            mappings = gson.fromJson(rawMappings, Mappings.class);
        } else {
            mappings = new Mappings(new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashMap<>());
        }
    }

    public static String getMappedClass(String clazz) {
        return mappings.getClasses().getOrDefault(clazz, clazz);
    }

    public static String getMappedMethod(String owner, String name, String descriptor) {
        return mappings.getMethods().getOrDefault(owner + "/" + name + " " + ASMUtil.mapDesc(descriptor, mappings.getClasses()), name);
    }

    public static String getMappedField(String owner, String name) {
        return mappings.getFields().getOrDefault(owner + "/" + name, name);
    }

    private static String getMappings() {
        String location = System.getenv("PG_MAPPINGS_FILE");
        if (location != null) {
            File file = new File(location);
            if (!file.exists()) {
                return null;
            }
            try (FileInputStream stream = new FileInputStream(file)) {
                return new String(stream.readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
