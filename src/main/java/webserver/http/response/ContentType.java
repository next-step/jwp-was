package webserver.http.response;

import java.util.Arrays;

public enum ContentType {

    IMAGE("png", "image/png"),
    FONT_TTF("ttf", "application/x-font-ttf"),
    FONT_WOFF("woff", "application/x-font-woff"),
    JS("js", "text/javascript"),
    CSS("css", "text/css"),
    HTML("html", "text/html");

    public final String fileExtension;
    public final String type;

    ContentType(String fileExtension, String type) {
        this.fileExtension = fileExtension;
        this.type = type;
    }

    public static ContentType from(String fileExtension) {
        return Arrays.stream(values())
                .filter(contentType -> contentType.fileExtension.equals(fileExtension))
                .findFirst()
                .orElseThrow();
    }
}
