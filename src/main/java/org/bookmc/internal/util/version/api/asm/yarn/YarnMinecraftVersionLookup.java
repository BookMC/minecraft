package org.bookmc.internal.util.version.api.asm.yarn;

import org.bookmc.internal.util.version.api.GameVersion;
import org.bookmc.internal.util.version.api.VersionLookup;
import org.bookmc.internal.util.version.api.asm.yarn.utils.ASMUtil;
import org.bookmc.internal.util.version.api.asm.yarn.utils.YarnUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

public class YarnMinecraftVersionLookup implements VersionLookup {
    @Override
    public GameVersion find() {
        byte[] bytes = YarnUtils.getVersionLookupClass();
        if (bytes == null) {
            return null;
        }

        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);

        String version = null;
        boolean stable;

        for (MethodNode methodNode : node.methods) {
            if (methodNode.name.equals("<init>") && methodNode.desc.equals("()V")) {
                for (AbstractInsnNode insnNode : methodNode.instructions) {
                    if (insnNode instanceof FieldInsnNode insn) {
                        if (isIdField(insn)) {
                            version = getVersion(insn);
                        }
                        if (isStableField(insn)) {
                            stable = getStableValue(insn);
                            if (version != null) {
                                // The stable field should always be after the version field
                                return new GameVersion(version, stable);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isIdField(FieldInsnNode insn) {
        return insn.owner.equals("net/minecraft/MinecraftVersion") && insn.name.equals("name");
    }

    private String getVersion(FieldInsnNode insn) {
        return ((LdcInsnNode) insn.getPrevious()).cst.toString();
    }

    public boolean getStableValue(FieldInsnNode fieldInsnNode) {
        return ASMUtil.toBoolean(fieldInsnNode.getPrevious().getOpcode());
    }

    private boolean isStableField(FieldInsnNode insn) {
        return insn.owner.equals("net/minecraft/MinecraftVersion") && insn.name.equals("stable");
    }
}
