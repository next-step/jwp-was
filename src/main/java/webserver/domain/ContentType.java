package webserver.domain;

public enum ContentType {
    CSS("text/css", "/css"),
    EOT("application/vnd.ms-fontobject", "/fonts"),
    SVG("image/svg+xml", "/fonts"),
    TTF("application/x-font-ttf", "fonts"),
    WOFF("application/x-font-woff", "fonts"),
    WOFF2("application/x-font-woff", "fonts"),
    PNG("image/png", "images"),
    JS("text/javascript", "js");

    String type;
    String dir;

    ContentType(String type, String dir) {
        this.type = type;
        this.dir = dir;
    }

    public String type() {
        return type;
    }

    public String dir() {
        return dir;
    }

}
