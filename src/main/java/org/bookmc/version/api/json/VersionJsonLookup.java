package org.bookmc.version.api.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bookmc.version.api.GameVersion;
import org.bookmc.version.api.VersionLookup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VersionJsonLookup implements VersionLookup {
    @Override
    public GameVersion find() {
        try (InputStream stream = getClass().getResourceAsStream("/version.json")) {
            if (stream == null) {
                return null;
            }

            try (InputStreamReader reader = new InputStreamReader(stream)) {
                // We use the deprecated way for backwards compatibility :/
                JsonObject obj = new JsonParser().parse(reader).getAsJsonObject();
                if (obj.has("id") && obj.has("stable")) {
                    return new GameVersion(obj.get("id").getAsString(), obj.get("stable").getAsBoolean());
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
