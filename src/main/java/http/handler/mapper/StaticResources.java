package http.handler.mapper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum StaticResources {
    JS("text/html;charset=utf-8", ".js"),
    CSS("text/css;charset=utf-8", ".css"),
    TTF("application/x-font-ttf", ".ttf"),
    WOFF("text/html;charset=utf-8", ".woff"),
    WOFF2("font/woff2", ".woff2"),
    ;

    @Getter
    private String contentType;

    @Getter
    private String suffix;

    StaticResources(String prefix, String suffix) {
        this.contentType = prefix;
        this.suffix = suffix;
    }

    public static boolean matches(String url) {
        return Arrays.stream(values())
            .anyMatch(resource -> endsWithSuffix(url, resource.getSuffix()));
    }

    private static boolean endsWithSuffix(String url, String suffix) {
        return url.toLowerCase().endsWith(suffix);
    }

    public static String getContentType(String url) {
        return Arrays.stream(values())
            .filter(resource -> endsWithSuffix(url, resource.getSuffix()))
            .findFirst()
            .map(StaticResources::getContentType)
            .orElse("");
    }
}
