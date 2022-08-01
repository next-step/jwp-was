package webserver.http;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "*/*"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "application/x-font-ttf"),
    OTF("otf", "application/x-font-opentype"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    EOT("eot", "application/vnd.ms-fontobject"),
    SFNT("sfnt", "application/font-sfnt"),
    ICO("ico", "image/x-icon");

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public static boolean isFileExtension(String text) {
        return Arrays.stream(values())
                .map(ContentType::getExtension)
                .anyMatch(extension -> extension.equals(text));
    }

    public String getExtension() {
        return extension;
    }

    public String getValue() {
        return value;
    }
}
