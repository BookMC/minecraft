package org.bookmc.internal.launch.patch.client;

import org.bookmc.internal.MinecraftCommon;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.bookmc.internal.util.version.MinecraftVersionLookup;
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
        if (MinecraftVersionLookup.find().id().startsWith("1.14")) {
            return name.equals("init") ||
                name.equals("au");
        } else {
            return name.equals("<init>");
        }
    }

    private boolean isSetPhase(MethodInsnNode insnNode) {
        return insnNode.name.equals("a") ||
            insnNode.name.equals("setPhase");
    }
}