package org.bookmc.internal.launch.patch.client.common;

import org.bookmc.internal.launch.patch.MinecraftPatch;
import org.bookmc.internal.util.constants.Constants;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ClientBrandRetrieverPatch implements MinecraftPatch {
    @Override
    public String[] getTargetClasses() {
        return new String[]{"net.minecraft.client.ClientBrandRetriever"};
    }

    @Override
    public void transform(ClassNode classNode) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("getClientModName")) {
                methodNode.instructions.clear();

                methodNode.instructions.add(new LdcInsnNode(Constants.GAME_BRANDING));
                methodNode.instructions.add(new InsnNode(Opcodes.ARETURN));
                break;
            }
        }
    }
}
