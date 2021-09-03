package org.bookmc.internal.util.version;

import org.bookmc.internal.util.version.api.GameVersion;
import org.bookmc.internal.util.version.api.VersionLookup;
import org.bookmc.internal.util.version.api.asm.constant.BookVersionMinecraftLookup;
import org.bookmc.internal.util.version.api.asm.yarn.YarnMinecraftVersionLookup;
import org.bookmc.internal.util.version.api.json.VersionJsonLookup;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MinecraftVersionLookup {
    private static final Set<VersionLookup> lookups = new HashSet<>();
    private static GameVersion version = null;

    static {
        addLookup(new VersionJsonLookup());
        addLookup(new YarnMinecraftVersionLookup());
        addLookup(new BookVersionMinecraftLookup());
    }

    public static GameVersion find() {
        if (version == null) {
            for (VersionLookup versionLookup : lookups) {
                GameVersion version = versionLookup.find();
                if (version != null) {
                    MinecraftVersionLookup.version = version;
                    break;
                }
            }
        }

        return version;
    }

    public static Set<VersionLookup> getLookups() {
        return Collections.unmodifiableSet(lookups);
    }

    public static void addLookup(VersionLookup lookup) {
        lookups.add(lookup);
    }
}
