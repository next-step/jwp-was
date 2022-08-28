package webserver.http;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ContentType {
    HTML("html", "text/html"),
    ICO("ico", "image/x-icon"),
    CSS("css", "text/css"),
    JS("js", "*/*"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "application/x-font-ttf"),
    OTF("otf", "application/x-font-opentype"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    EOT("eot", "application/vnd.ms-fontobject"),
    SFNT("sfnt", "application/font-sfnt");

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public static boolean isStaticExtension(String path) {
        List<ContentType> contentTypes = staticFiles();

        return contentTypes.stream()
                .anyMatch(_extension -> path.endsWith(_extension.getExtension()));
    }

    public static ContentType getContentTypeFromExtension(String extension) {
        return Arrays.stream(values())
                .filter(contentType -> contentType.getExtension().equals(extension))
                .findFirst().get();
    }

    private static List<ContentType> staticFiles() {
        return Arrays.stream(values())
                .filter(contentType -> contentType != ContentType.HTML)
                .filter(contentType -> contentType != ContentType.ICO)
                .collect(Collectors.toList());
    }

    public String getExtension() {
        return extension;
    }

    public String getValue() {
        return value;
    }
}
