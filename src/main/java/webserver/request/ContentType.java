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
    private final String mediaType;

    ContentType(final String fileExtension, final String mediaType) {
        this.fileExtension = fileExtension;
        this.mediaType = mediaType;
    }

    public static ContentType of(final String fileExtension) {
        return Arrays.stream(values())
            .filter(contentType -> contentType.fileExtension.equals(fileExtension))
            .findFirst()
            .orElse(HTML);
    }

    public String getMediaType() {
        return mediaType;
    }
}
