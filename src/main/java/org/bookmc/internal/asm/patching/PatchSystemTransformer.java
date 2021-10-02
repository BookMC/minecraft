package org.bookmc.internal.asm.patching;

import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.bookmc.internal.launch.patch.client.EntrypointPatch;
import org.bookmc.internal.launch.patch.client.common.ClientBrandRetrieverPatch;
import org.bookmc.internal.launch.patch.client.legacy.LegacyEntrypointPatch;
import org.bookmc.internal.launch.patch.server.ServerEntrypointPatch;
import org.bookmc.internal.launch.patch.server.common.ServerBrandingPatch;
import org.bookmc.internal.launch.patch.server.legacy.LegacyServerEntrypointPatch;
import org.bookmc.internal.util.obfuscation.BookObfuscationUtil;
import org.bookmc.loader.api.classloader.transformers.BookTransformer;
import org.bookmc.loader.libs.guava.common.collect.ArrayListMultimap;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.List;

public class PatchSystemTransformer implements BookTransformer {
    private final ArrayListMultimap<String, MinecraftPatch> patches = ArrayListMultimap.create();

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
    public byte[] proposeTransformation(String name, byte[] clazz) {
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
        return writer.toByteArray();

    }

    private void registerPatch(MinecraftPatch patch) {
        for (String target : patch.getTargetClasses()) {
            patches.put(target, patch);
        }
    }
}
