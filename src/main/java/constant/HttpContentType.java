package constant;

import java.util.Arrays;

public enum HttpContentType {
    TEXT_HTML(".html", "./templates", "text/html;charset=utf-8"),
    FAVICON(".ico", "./templates", "text/html;charset=utf-8"),
    TEXT_CSS(".css", "./static", "text/css,*/*;q=0.1"),
    APPLICATION_JAVASCRIPT(".js", "./static", "application/javascript"),
    FONT_WOFF(".woff", "./static", "application/x-font-woff"),
    FONT_WOFF2(".woff2","./static", "application/font-woff2"),
    FONT_EOT(".eot", "./static", "application/vnd.ms-fontobject"),
    SVG(".svg", "./static", "svg=image/svg+xml"),
    FONT_TTF(".ttf", "./static", "application/x-font-ttf"),
    PNG(".png", "./static", "image/png");

    private String type;
    private String resourcePath;
    private String value;

    HttpContentType(String type, String resourcePath, String value) {
        this.type = type;
        this.resourcePath = resourcePath;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getValue() {
        return value;
    }

    public static HttpContentType of(String template) {
        return Arrays.stream(values())
            .filter(contentType -> template.endsWith(contentType.type))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(Message.NOT_FOUND_CONTENT_TYPE.getValue()));
    }

}
