package webserver.http;

import java.util.Arrays;

public enum StaticContentType {
    HTML(".html", "./templates"),
    JS(".js", "./static"),
    CSS(".css", "./static"),
    ICO(".ico", "./templates"),
    EOT(".eot", "./static"),
    SVG(".svg", "./static"),
    TTF(".ttf", "./static"),
    WOFF(".woff", "./static"),
    WOFF2(".woff2", "./static"),
    PNG(".png", "./static");

    private final String type;
    private final String path;

    StaticContentType(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public static StaticContentType fromRequestPath(final String path) {
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
}
