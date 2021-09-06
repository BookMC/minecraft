package org.bookmc.internal.launch.patch.client.legacy;

import org.bookmc.internal.LoaderInternal;
import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.objectweb.asm.tree.*;

public class LegacyEntrypointPatch implements MinecraftPatch {
    @Override
    public String[] getTargetClasses() {
        return new String[]{"net.minecraft.client.Minecraft", "ave", "bcd", "bcx", "cft"};
    }

    @Override
    public void transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (isInit(methodNode)) {
                for (AbstractInsnNode insnNode : methodNode.instructions) {
                    if (insnNode instanceof MethodInsnNode methodInsnNode) {
                        if (isSetPhase(methodInsnNode)) {
                            if (methodInsnNode.getPrevious() instanceof LdcInsnNode previous) {
                                if (previous.cst.equals("Startup")) {
                                    methodNode.instructions.insert(methodInsnNode, createHook(LoaderInternal.class, "load", "()V"));
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    private boolean isInit(MethodNode methodNode) {
        return methodNode.name.equals("startGame") ||
            methodNode.name.equals("am") ||
            methodNode.name.equals("init") ||
            methodNode.name.equals("an") ||
            methodNode.name.equals("launch") ||
            methodNode.name.equals("a");
    }

    private boolean isSetPhase(MethodInsnNode methodInsnNode) {
        return methodInsnNode.name.equals("checkGLError") ||
            methodInsnNode.name.equals("b") && methodInsnNode.owner.equals("ave") ||
            methodInsnNode.name.equals("setRenderPhase") ||
            methodInsnNode.name.equals("a") ||
            methodInsnNode.name.equals("pushPhase") ||
            methodInsnNode.name.equals("c");
    }
}
