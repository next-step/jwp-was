package http.request;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html"),
    FORM_DATA("form-data", "multipart/form-data"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    IMAGE("image", "image/jpeg"),
    WOFF("woff", "font/woff2"),
    TTF("ttf", "application/octet-stream"),
    ICO("ico", "image/x-icon");

    private final String extension;
    private final String message;

    ContentType(String extension, String message) {
        this.extension = extension;
        this.message = message;
    }

    public static ContentType of(String fileExtension) {
        return Arrays.stream(values())
            .filter(it -> it.extension.equalsIgnoreCase(fileExtension))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("호환되지 않는 확장자입니다. fileExtension=" + fileExtension));
    }

    public String getMessage() {
        return message;
    }
}
