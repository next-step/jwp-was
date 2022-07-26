package webserver.http;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "*/*"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "application/x-font-ttf"),
    OTF("otf", "application/x-font-opentype"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    EOT("eot", "application/vnd.ms-fontobject"),
    SFNT("sfnt", "application/font-sfnt"),
    ICO("ico", "image/x-icon");

    private final String extension;
    private final String value;

    ContentType(String extension, String value) {
        this.extension = extension;
        this.value = value;
    }

    public String getExtension() {
        return extension;
    }

    public String getValue() {
        return value;
    }
}
