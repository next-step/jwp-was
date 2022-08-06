package webserver.http.model;

import java.util.Arrays;

public enum Extension {
    HTML("html", "./templates"),
    ICO("ico", "./templates"),
    JS("js", "./static"),
    CSS("css", "./static"),
    NOT_STATIC_RESOURCE("", "");

    private final String extension;
    private final String parentPath;

    Extension(String extension, String parentPath) {
        this.extension = extension;
        this.parentPath = parentPath;
    }

    public static String fullPath(String childPath) {
        String[] splitChildPath = childPath.split("\\.");
        String extension = splitChildPath[splitChildPath.length - 1];
        return parentPathByExtension(extension) + childPath;
    }

    public static String parentPathByExtension(String extension) {
        return Arrays.stream(values()).filter(extensionEnum -> extensionEnum.extension.equals(extension))
                .map(extensionEnum -> extensionEnum.parentPath).findFirst().orElse("");
    }

    public static Extension getEnum(String extension) {
        return Arrays.stream(values()).filter(extensionEnum -> extension.equals(extensionEnum.extension)).findFirst().orElse(NOT_STATIC_RESOURCE);
    }
}
