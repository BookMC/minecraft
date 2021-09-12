package org.bookmc.internal.asm.compat;

import org.bookmc.loader.api.transformer.QuiltTransformer;
import org.bookmc.loader.impl.launch.BookLauncher;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class GuavaCompatibility implements QuiltTransformer {
    @Override
    public byte[] transform(String name, byte[] clazz) {
        if (BookLauncher.isDevelopment()) {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(clazz);
            reader.accept(node, 0);

            for (MethodNode methodNode : node.methods) {
                for (AbstractInsnNode i : methodNode.instructions) {
                    patchFirstNonNull(i);
                    patchEmptyIterator(i);
                    patchAddCallback(i, methodNode.instructions);
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            node.accept(writer);

            return writer.toByteArray();
        }

        return clazz;
    }

    private void patchFirstNonNull(AbstractInsnNode insn) {
        if (insn instanceof MethodInsnNode insnNode) {
            if (insnNode.owner.equals("com/google/common/base/Objects") && insnNode.name.equals("firstNonNull")) {
                insnNode.owner = "com/google/common/base/MoreObjects";
            }
        }
    }

    private void patchEmptyIterator(AbstractInsnNode insn) {
        if (insn instanceof MethodInsnNode insnNode) {
            if (insnNode.owner.equals("com/google/common/collect/Iterators") && insnNode.name.equals("emptyIterator")) {
                insnNode.owner = "java/util/Collections";
                insnNode.name = "emptyIterator";
                insnNode.desc = "()Ljava/util/Iterator;";
            }
        }
    }

    private void patchAddCallback(AbstractInsnNode insnNode, InsnList list) {
        if (insnNode instanceof MethodInsnNode insn) {
            if (insn.owner.equals("com/google/common/util/concurrent/Futures") && insn.name.equals("addCallback")) {
                list.insertBefore(insn, addDefaultCallback());
                insn.desc = "(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;Ljava/util/concurrent/Executor;)V";
            }
        }
    }

    private InsnList addDefaultCallback() {
        InsnList list = new InsnList();
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/google/common/util/concurrent/MoreExecutors", "directExecutor", "()Ljava/util/concurrent/Executor;"));
        return list;
    }
}
