package org.bookmc.internal.launch.patch.server.legacy;

import org.bookmc.internal.LoaderInternal;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class LegacyServerEntrypointPatch implements MinecraftPatch {
    @Override
    public String getTargetClass() {
        return "net.minecraft.server.dedicated.DedicatedServer";
    }

    @Override
    public void transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (isStartServer(methodNode)) {
                for (AbstractInsnNode insnNode : methodNode.instructions) {
                    if (insnNode instanceof MethodInsnNode methodInsnNode) {
                        if (isPlayerList(methodInsnNode)) {
                            methodNode.instructions.insert(insnNode, createHook(LoaderInternal.class, "load", "()V"));
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    public boolean isStartServer(MethodNode methodNode) {
        return methodNode.name.equals("startServer") ||
            methodNode.name.equals("i") ||
            methodNode.name.equals("init") ||
            methodNode.name.equals("j") ||
            methodNode.name.equals("d");
    }

    public boolean isPlayerList(MethodInsnNode methodInsnNode) {
        return methodInsnNode.name.equals("setConfigManager") ||
            methodInsnNode.name.equals("a") ||
            methodInsnNode.name.equals("setPlayerList");
    }
}
