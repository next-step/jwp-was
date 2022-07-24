package webserver.request;

import java.util.Arrays;

public enum ContentType {

    CSS("css", "text/css"),
    JAVASCRIPT("js", "application/javascript"),
    HTML("html", "text/html"),
    TEXT("", "text/plain"),
    WOFF("woff", "application/font-woff"),
    PNG("png", "image/png");

    private final String fileExtension;
    private final String contentType;

    ContentType(final String fileExtension, final String contentType) {
        this.fileExtension = fileExtension;
        this.contentType = contentType;
    }

    public static ContentType of(final String fileExtension) {
        return Arrays.stream(values())
            .filter(contentType -> contentType.fileExtension.equals(fileExtension))
            .findFirst()
            .orElse(HTML);
    }

    public String getContentType() {
        return contentType;
    }
}
