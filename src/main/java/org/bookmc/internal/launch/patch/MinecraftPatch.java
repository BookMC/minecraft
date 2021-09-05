package org.bookmc.internal.launch.patch;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;

public interface MinecraftPatch {
    String[] getTargetClasses();

    void transform(ClassNode classNode);

    default MethodInsnNode createHook(Class<?> hookClass, String hookMethodName, String hookMethodDescriptor) {
        return new MethodInsnNode(Opcodes.INVOKESTATIC, hookClass.getName().replace(".", "/"), hookMethodName, hookMethodDescriptor);
    }
}
