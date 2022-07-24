package webserver.http;

import java.util.Arrays;

public enum ContentType {

    TEXT_HTML("html", "text/html;charset=utf-8"),
    TEXT_CSS("css", "text/css"),
    TEXT_JAVASCRIPT("js", "text/javascript"),
    APPLICATION_X_FONT_WOFF("woff", "application/x-font-woff"),
    APPLICATION_X_FONT_TTF("ttf", "application/x-font-ttf"),
    IMAGE_X_ICON("ico", "image/x-icon"),
    ;

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public static ContentType from(String path) {
        return Arrays.stream(values())
            .filter(type -> path.endsWith(type.extension))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public String getExtension() {
        return extension;
    }

    public String getValue() {
        return value;
    }
}
