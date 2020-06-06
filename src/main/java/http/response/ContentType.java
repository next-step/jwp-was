package http.response;

import java.util.Arrays;

public enum ContentType {
    CSS("text/css", ".css"),
    JAVASCRIPT("text/html;charset=utf-8", ".js"),
    WOFF("text/html;charset=utf-8", ".woff"),
    WOFF2("font/woff2", ".woff2"),
    HTML("text/html", ".html");

    private String contentType;
    private String suffix;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    ContentType(String contentType, String suffix) {
        this.contentType = contentType;
        this.suffix = suffix;
    }

    public static ContentType findContentType(String url){
        return Arrays.stream(values())
                .filter(v -> url.endsWith(v.suffix))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found matched Content-Type!"));
    }
}
