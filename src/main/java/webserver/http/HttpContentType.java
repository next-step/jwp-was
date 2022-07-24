package webserver.http;

import java.util.Arrays;

public enum HttpContentType {
    TEXT_HTML("html", "./templates", "text/html;charset=utf-8"),
    FAVICON("ico", "./templates", "text/html;charset=utf-8"),
    TEXT_CSS("css", "./static", "text/css,*/*;q=0.1"),
    APPLICATION_JAVASCRIPT("js", "./static", "application/javascript"),
    UNKNOWN_TYPE("", "./static", "unknown");

    private String type;
    private String resourcePath;
    private String value;

    HttpContentType(String type, String resourcePath, String value) {
        this.type = type;
        this.resourcePath = resourcePath;
        this.value = value;
    }

    public static HttpContentType of(String type) {
        return Arrays.stream(values())
                .filter(contentType -> contentType.type.equals(type))
                .findFirst()
                .orElse(UNKNOWN_TYPE);
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
}
