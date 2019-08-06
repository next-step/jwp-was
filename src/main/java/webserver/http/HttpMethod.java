package webserver.http;

import java.util.Arrays;

public enum HttpMethod {

    GET, POST;

    public static HttpMethod of(final String rawHttpMethod) {
        return Arrays.stream(values())
                .filter(HttpMethod -> HttpMethod.equalsName(rawHttpMethod))
                .findFirst()
                .orElseThrow();
    }

    private boolean equalsName(final String rawHttpMethod) {
        return name().equals(rawHttpMethod.toUpperCase());
    }
}
