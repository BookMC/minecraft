package org.bookmc.common.asm;

import org.bookmc.loader.api.launch.transform.QuiltRemapper;
import org.bookmc.loader.impl.launch.Launcher;
import org.bookmc.srg.SrgProcessor;
import org.bookmc.srg.output.MappedField;
import org.bookmc.srg.output.MappedMethod;
import org.bookmc.srg.output.SrgOutput;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

import java.io.File;

public class MinecraftRemapper extends Remapper implements QuiltRemapper {
    private SrgOutput output = null;

    public MinecraftRemapper() {
        File mappings = Launcher.getMappings();

        if (mappings != null) {
            output = new SrgProcessor(mappings).process();
        }
    }

    @Override
    public byte[] transform(String name, byte[] clazz) {
        if (output != null) {
            ClassReader reader = new ClassReader(clazz);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassRemapper remapper = new ClassRemapper(writer, this);
            reader.accept(remapper, ClassReader.EXPAND_FRAMES);
            return writer.toByteArray();
        } else {
            return clazz;
        }
    }

    @Override
    public String mapFieldName(String owner, String name, String descriptor) {
        MappedField field = output.getField(owner, name);
        if (field != null) {
            return field.getDeobfuscatedName();
        }

        return super.mapFieldName(owner, name, descriptor);
    }

    @Override
    public String map(String internalName) {
        String deobf = output.getClass(internalName);
        if (deobf != null) {
            return deobf;
        }

        int dollar = internalName.lastIndexOf('$');
        if (dollar > -1) {
            return map(internalName.substring(0, dollar)) + "$" + internalName.substring(dollar + 1);
        }

        return super.map(internalName);
    }

    @Override
    public String mapMethodName(String owner, String name, String descriptor) {
        MappedMethod method = output.getMethod(owner, name, descriptor);
        if (method != null) {
            return method.getDeobfuscatedName();
        }

        return super.mapMethodName(owner, name, descriptor);
    }

    @Override
    public String mapSignature(String signature, boolean typeSignature) {
        if (signature != null && signature.contains("!*")) {
            return null;
        }

        return super.mapSignature(signature, typeSignature);
    }
}
