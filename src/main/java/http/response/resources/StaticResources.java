package http.response.resources;

import java.util.Arrays;

public enum StaticResources {
    JAVASCRIPT(".js"),
    CSS(".css"),
    WOFF(".woff"),
    WOFF2(".woff2"),
    TTF(".ttf");

    private String suffix;

    StaticResources(String suffix) {
        this.suffix = suffix;
    }

    public static boolean match(String url) {
        return Arrays.stream(values())
                .anyMatch(v -> url.endsWith(v.suffix));
    }
}
