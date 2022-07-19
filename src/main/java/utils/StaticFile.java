package utils;

import java.util.Arrays;

public enum StaticFile {

    CSS("css"),
    FONTS("fonts"),
    IMAGES("images"),
    JS("js");

    private String name;

    StaticFile(String name) {
        this.name = name;
    }

    public static boolean isStatic(String name) {
        return Arrays.stream(StaticFile.values())
               .anyMatch(value -> name.contains(value.getName()));
    }

    public String getName() {
        return name;
    }
}
