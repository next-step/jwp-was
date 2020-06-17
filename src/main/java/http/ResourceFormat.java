package http;

import java.util.EnumSet;

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

    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";
    private static final EnumSet<ContentType> typesInTemplates = EnumSet.of(ContentType.HTML, ContentType.ICO);

    ContentType(String mediaType) {
        this.mediaType = mediaType;
    }

    public static ContentType from(Uri uri) {
        return valueOf(uri.getExtension().toUpperCase());
    }

    public String toStringWithCharsetUTF8() {
        return this.mediaType + SEMI_COLON + CHARSET_UTF_8;
    }

    public static String toRelativePath(Uri uri) {
        String prefix = typesInTemplates.contains(from(uri)) ? TEMPLATE_PATH : STATIC_PATH;
        return prefix + uri.getPath();
    }
}
