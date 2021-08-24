package org.bookmc.version.api.asm.constant;

import org.bookmc.version.api.GameVersion;
import org.bookmc.version.api.VersionLookup;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;
import java.io.InputStream;

public class BookVersionMinecraftLookup implements VersionLookup {
    private static final String BOOK_VERSION = "org.bookmc.version.BookVersion";

    @Override
    public GameVersion find() {
        byte[] bytes = getBytes();
        if (bytes == null) {
            return null;
        }

        ClassReader reader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);
        String version = null;
        for (FieldNode fieldNode : classNode.fields) {
            if (fieldNode.name.equals("MINECRAFT_VERSION")) {
                version = fieldNode.value.toString();
            }
            if (fieldNode.name.equals("STABLE")) {
                if (version != null) {
                    return new GameVersion(version, (Integer) fieldNode.value != 0);
                }
            }
        }
        return null;
    }

    private byte[] getBytes() {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + BOOK_VERSION.replace(".", "/").concat(".class"))) {
            if (inputStream != null) {
                return inputStream.readAllBytes();
            }
        } catch (IOException ignored) {

        }

        return null;
    }
}
