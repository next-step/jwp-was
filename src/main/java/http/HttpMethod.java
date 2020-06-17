package http;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod getHttpMethod(final String value) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
