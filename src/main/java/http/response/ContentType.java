package http.response;

public enum ContentType {

    HTML("text/html"),
    ICO("image/x-icon"),
    PLAIN("text/plain"),
    CSS("text/css"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    JS("application/javascript"),
    EOT("application/vnd.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("application/x-font-ttf"),
    WOFF("application/font-woff"),
    WOFF2("application/font-woff2");


    private final String mediaType;
    public static final String CHARSET_UTF_8 = "charset=utf-8";
    private static final String SEMI_COLON = ";";

    ContentType(String mediaType) {
        this.mediaType = mediaType;
    }

    public static ContentType from(String extension) {
        return valueOf(extension.toUpperCase());
    }

    public String toStringWithCharsetUTF8() {
        return this.mediaType + SEMI_COLON + CHARSET_UTF_8;
    }
}
