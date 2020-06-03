package http;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod findByName(String name) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }
}
