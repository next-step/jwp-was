package http;

public enum ContentType {

    PLAIN("text/plain"),
    CSS("text/css"),
    HTML("text/html;"),
    JPEG("image/jpeg"),
    GIF("image/gif"),
    PNG("image/png"),
    JS("application/javascript"),
    JSON("application/json"),
    X_WWW_FORM_URLENCODED("application/x-www-form-unlencoded"),
    EOT("application/vnd.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("application/x-font-ttf"),
    WOFF("application/font-woff"),
    WOFF2("application/font-woff2"),
    ICO("image/x-icon");

    private final String mediaType;

    ContentType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType + ";charset=utf-8";
    }
}
