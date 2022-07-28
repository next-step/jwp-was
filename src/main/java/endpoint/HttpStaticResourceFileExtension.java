package endpoint;

import java.util.Arrays;

public enum HttpStaticResourceFileExtension {
    CSS("css"),
    HTML("html"),
    NONE(""),
    NOTHING("nothing");

    private String extensionName;

    HttpStaticResourceFileExtension(String extensionName) {
        this.extensionName = extensionName;
    }

    public static HttpStaticResourceFileExtension select(String extensionName) {
        return Arrays.stream(values())
                .filter(extension -> extension.extensionName.equals(extensionName))
                .findAny()
                .orElse(NOTHING);
    }
}
