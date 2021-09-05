package org.bookmc.internal.asm.patching;

import org.bookmc.external.transformer.QuiltTransformer;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.bookmc.internal.launch.patch.client.EntrypointPatch;
import org.bookmc.internal.launch.patch.client.common.ClientBrandRetrieverPatch;
import org.bookmc.internal.launch.patch.client.legacy.LegacyEntrypointPatch;
import org.bookmc.internal.launch.patch.server.ServerEntrypointPatch;
import org.bookmc.internal.launch.patch.server.common.ServerBrandingPatch;
import org.bookmc.internal.launch.patch.server.legacy.LegacyServerEntrypointPatch;
import org.bookmc.internal.util.obfuscation.BookObfuscationUtil;
import org.bookmc.loader.impl.launch.Launcher;
import org.bookmc.loader.libs.guava.common.collect.ArrayListMultimap;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PatchSystemTransformer implements QuiltTransformer {
    private final ArrayListMultimap<String, MinecraftPatch> patches = ArrayListMultimap.create();

    private final boolean export = Boolean.parseBoolean(System.getProperty("book.patch.export", Launcher.isDevelopment() ? "true" : "false"));
    private final File outputFolder = new File(Launcher.getGameProvider().getGameDirectory(), System.getProperty("book.patch.export.directory", ".book-out/classes"));

    public PatchSystemTransformer() {
        // Client
        registerPatch(new ClientBrandRetrieverPatch());
        registerPatch(new LegacyEntrypointPatch());
        registerPatch(new EntrypointPatch());

        // Server
        registerPatch(new LegacyServerEntrypointPatch());
        registerPatch(new ServerEntrypointPatch());
        registerPatch(new ServerBrandingPatch());
    }

    @Override
    public byte[] transform(String name, byte[] clazz) {
        String clazzName = BookObfuscationUtil.getMappedClass(name);
        List<MinecraftPatch> minecraftPatches = patches.get(clazzName);
        if (minecraftPatches.isEmpty()) return clazz;

        ClassReader reader = new ClassReader(clazz);
        ClassNode classNode = new ClassNode();

        reader.accept(classNode, 0);

        for (MinecraftPatch patch : minecraftPatches) {
            patch.transform(classNode);
        }

        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        byte[] bytes = writer.toByteArray();
        if (export) {
            File f = new File(outputFolder, name.replace(".", "/").concat(".class"));
            File parent = f.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            try {
                f.delete();
                f.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    fos.write(bytes, 0, bytes.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    private void registerPatch(MinecraftPatch patch) {
        patches.put(patch.getTargetClass(), patch);
    }
}
