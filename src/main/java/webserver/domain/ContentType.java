package webserver.domain;

public enum ContentType {
    HTML("html", "", "text/html;charset=utf-8"),
    CSS("css", "/css", "text/css"),
    EOT("eot", "/fonts", "application/vnd.ms-fontobject"),
    SVG("svg", "/fonts", "image/svg+xml"),
    TTF("ttf", "/fonts", "application/x-font-ttf"),
    WOFF("woff", "/fonts", "application/x-font-woff"),
    WOFF2("woff2", "/fonts", "application/x-font-woff"),
    PNG("png", "/images", "image/png"),
    JS("js", "/js", "text/javascript");

    String type;
    String extension;
    String dir;

    ContentType(String extension, String dir, String type) {
        this.type = type;
        this.dir = dir;
        this.extension = extension;
    }

    public String type() {
        return type;
    }

    public String extension() {
        return extension;
    }

    public String dir() {
        return dir;
    }

}
