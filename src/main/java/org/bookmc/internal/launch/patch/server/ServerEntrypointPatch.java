package org.bookmc.internal.launch.patch.server;

import org.bookmc.internal.MinecraftCommon;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ServerEntrypointPatch implements MinecraftPatch {
    @Override
    public String getTargetClass() {
        return "net.minecraft.server.dedicated.MinecraftDedicatedServer";
    }

    @Override
    public void transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (isStartServer(methodNode)) {
                for (AbstractInsnNode insnNode : methodNode.instructions) {
                    if (insnNode instanceof MethodInsnNode methodInsnNode) {
                        if (isPlayerManager(methodInsnNode)) {
                            methodNode.instructions.insert(insnNode, createHook(MinecraftCommon.class, "load", "()V"));
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    public boolean isStartServer(MethodNode methodNode) {
        return methodNode.name.equals("setupServer") ||
            methodNode.name.equals("d");
    }

    public boolean isPlayerManager(MethodInsnNode methodInsnNode) {
        return methodInsnNode.name.equals("setPlayerManager") ||
            methodInsnNode.name.equals("a");
    }
}
