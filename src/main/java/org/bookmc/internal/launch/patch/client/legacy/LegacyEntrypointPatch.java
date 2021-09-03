package org.bookmc.internal.launch.patch.client.legacy;

import org.bookmc.internal.MinecraftCommon;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.objectweb.asm.tree.*;

public class LegacyEntrypointPatch implements MinecraftPatch {
    @Override
    public String getTargetClass() {
        return "net.minecraft.client.Minecraft";
    }

    @Override
    public void transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("startGame") || methodNode.name.equals("am")) {
                for (AbstractInsnNode insnNode : methodNode.instructions) {
                    if (insnNode instanceof MethodInsnNode methodInsnNode) {
                        if (methodInsnNode.name.equals("checkGLError") || methodInsnNode.name.equals("b")) {
                            if (methodInsnNode.getPrevious() instanceof LdcInsnNode previous) {
                                if (previous.cst.equals("Startup")) {
                                    methodNode.instructions.insert(methodInsnNode, createHook(MinecraftCommon.class, "load", "()V"));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
