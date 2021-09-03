package org.bookmc.internal.launch.patch.client;

import org.bookmc.internal.MinecraftCommon;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.objectweb.asm.tree.*;

public class EntrypointPatch implements MinecraftPatch {
    @Override
    public String getTargetClass() {
        return "net.minecraft.client.MinecraftClient";
    }

    @Override
    public void transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (isInit(methodNode.name)) {
                for (AbstractInsnNode insnNode : methodNode.instructions) {
                    if (insnNode instanceof MethodInsnNode methodInsnNode) {
                        if (isSetPhase(methodInsnNode)) {
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

    private boolean isInit(String name) {
        return name.equals("init") ||
            name.equals("au");
    }

    private boolean isSetPhase(MethodInsnNode insnNode) {
        return insnNode.name.equals("a") ||
            insnNode.name.equals("setPhase");
    }
}
