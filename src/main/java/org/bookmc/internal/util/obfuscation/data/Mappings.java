package org.bookmc.internal.util.obfuscation.data;

import java.util.LinkedHashMap;

@SuppressWarnings("ClassCanBeRecord") // Gson has issues
public class Mappings {
    private final LinkedHashMap<String, String> classes;
    private final LinkedHashMap<String, String> methods;
    private final LinkedHashMap<String, String> fields;

    public Mappings(LinkedHashMap<String, String> classes, LinkedHashMap<String, String> methods, LinkedHashMap<String, String> fields) {
        this.classes = classes;
        this.methods = methods;
        this.fields = fields;
    }

    public LinkedHashMap<String, String> getClasses() {
        return classes;
    }

    public LinkedHashMap<String, String> getMethods() {
        return methods;
    }

    public LinkedHashMap<String, String> getFields() {
        return fields;
    }
}