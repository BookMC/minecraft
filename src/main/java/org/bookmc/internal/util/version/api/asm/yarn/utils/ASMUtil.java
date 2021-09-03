package org.bookmc.internal.util.version.api.asm.yarn.utils;

import org.objectweb.asm.Opcodes;

public class ASMUtil {
    public static boolean toBoolean(int opcode) {
        if (opcode == Opcodes.ICONST_0) {
            return false;
        } else if (opcode == Opcodes.ICONST_1) {
            return true;
        }

        throw new UnsupportedOperationException("The given opcode was NOT a boolean!");
    }
}
