package webserver.http;

import java.util.Arrays;

public enum HttpContentType {
    HTML(".html", "./templates", "text/html; charset=utf-8"),
    JS(".js", "./static", "application/javascript; charset=utf-8"),
    CSS(".css", "./static", "text/css,*/*;q=0.1"),
    ICO(".ico", "./templates", "text/html;charset=utf-8"),
    EOT(".eot", "./static", "application/vnd.ms-fontobject"),
    SVG(".svg", "./static", "image/svg+xml"),
    TTF(".ttf", "./static", "application/x-font-ttf"),
    WOFF(".woff", "./static", "application/font-woff"),
    WOFF2(".woff2", "./static", "application/font-woff2"),
    PNG(".png", "./static", "image/png");

    private final String type;
    private final String path;
    private final String contentType;

    HttpContentType(String type, String path, String contentType) {
        this.type = type;
        this.path = path;
        this.contentType = contentType;
    }

    public static HttpContentType fromRequestPath(final String path) {
        return Arrays.stream(values())
                .filter(contentType -> path.contains(contentType.type))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public String getContentType() {
        return contentType;
    }
}
