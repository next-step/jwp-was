package webserver;

import java.util.Arrays;

public enum ContentType {

    CSS("css", "text/css"),
    JAVASCRIPT("js", "application/javascript"),
    HTML("html", "text/html;charset=utf-8"),
    WOFF("woff", "application/font-woff"),
    TTF("ttf", "application/x-font-ttf"),
    PNG("png", "image/png"),
    IMAGE_X_ICON("ico", "image/x-icon");

    private String fileExtension;
    private String mediaType;

    ContentType(String fileExtension, String mediaType) {
        this.fileExtension = fileExtension;
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType;
    }

    private String getFileExtension() {
        return fileExtension;
    }

    public static ContentType from(String path) {
        return Arrays.stream(values())
                .filter(val -> path.endsWith(val.getFileExtension()))
                .findFirst()
                .orElse(HTML);
    }
}
