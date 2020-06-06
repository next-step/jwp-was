package http.response.resources;

import java.util.Arrays;

public enum TemplateResources {
    HTML(".html"),
    FAVICON(".ico");

    private String suffix;

    TemplateResources(String suffix) {
        this.suffix = suffix;
    }

    public static boolean match(String url) {
        return Arrays.stream(values())
                .anyMatch(v -> url.endsWith(v.suffix));
    }
}
