package org.bookmc.version.api;

/**
 * The VersionLookup API allows for multiple methods of looking up for methods
 * it also means that we can try detect the accuracy of the version by checking
 * it against other lookups and methods that can be implemented. The API is a simple
 * API with potential for version-independent accurate game version lookups.
 *
 * @author ChachyDev
 * @since 0.5.0
 */
public interface VersionLookup {
    /**
     * An abstracted method which should return the version the lookup believes the game is.
     * If it couldn't find anything then it should simply return null and {@link org.bookmc.version.MinecraftVersionLookup}
     * will handle it all.
     *
     * @return The (maybe) located version of the game.
     */
    GameVersion find();
}
