package org.bookmc.internal.util;

import org.objectweb.asm.Type;

import java.util.Map;

public class ASMUtil {
    public static String mapDesc(String descriptor, Map<String, String> classes) {
        return mapType(Type.getType(descriptor), classes).getDescriptor();
    }

    private static Type mapType(Type type, Map<String, String> classes) {
        switch (type.getSort()) {
            case 9:
                String remappedDescriptor = "[".repeat(Math.max(0, type.getDimensions())) +
                    mapType(type.getElementType(), classes).getDescriptor();
                return Type.getType(remappedDescriptor);
            case 10:
                String remappedInternalName = classes.getOrDefault(type.getInternalName(), type.getInternalName());
                return remappedInternalName != null ? Type.getObjectType(remappedInternalName) : type;
            case 11:
                return Type.getMethodType(mapMethodDesc(type.getDescriptor(), classes));
            default:
                return type;
        }
    }

    public static String mapMethodDesc(String methodDescriptor, Map<String, String> classes) {
        if ("()V".equals(methodDescriptor)) {
            return methodDescriptor;
        } else {
            StringBuilder stringBuilder = new StringBuilder("(");
            Type[] var3 = Type.getArgumentTypes(methodDescriptor);

            for (Type argumentType : var3) {
                stringBuilder.append(mapType(argumentType, classes).getDescriptor());
            }

            Type returnType = Type.getReturnType(methodDescriptor);
            if (returnType == Type.VOID_TYPE) {
                stringBuilder.append(")V");
            } else {
                stringBuilder.append(')').append(mapType(returnType, classes).getDescriptor());
            }

            return stringBuilder.toString();
        }
    }
}
