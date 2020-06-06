package webserver.resources;

import java.util.Arrays;

public enum Template {
    HTML(".html"),
    FAVICON(".ico");

    private String suffix;

    Template(String suffix) {
        this.suffix = suffix;
    }

    public static boolean match(String url) {
        return Arrays.stream(values())
                .anyMatch(v -> url.endsWith(v.suffix));
    }
}
