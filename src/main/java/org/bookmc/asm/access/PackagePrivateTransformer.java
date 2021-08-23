package org.bookmc.asm.access;

import org.bookmc.loader.api.launch.transform.QuiltTransformer;
import org.objectweb.asm.*;

public class PackagePrivateTransformer implements QuiltTransformer {
    @Override
    public byte[] transform(String name, byte[] clazz) {
        ClassReader reader = new ClassReader(clazz);
        ClassWriter writer = new ClassWriter(0);
        reader.accept(new PackagePrivateVisitor(writer), 0);
        return writer.toByteArray();
    }

    private static class PackagePrivateVisitor extends ClassVisitor {
        public PackagePrivateVisitor(ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, transformAccess(access), name, signature, superName, interfaces);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            return super.visitField(transformAccess(access), name, descriptor, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return super.visitMethod(transformAccess(access), name, descriptor, signature, exceptions);
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            super.visitInnerClass(name, outerName, innerName, transformAccess(access));
        }

        private int transformAccess(int access) {
            return ((access & 0x7) != Opcodes.ACC_PRIVATE) ? (access & (~0x7)) | Opcodes.ACC_PUBLIC : access;
        }
    }
}
