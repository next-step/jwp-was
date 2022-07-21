package webserver.domain;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String httpMethod;

    HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public static HttpMethod of(String httpMethod) {
        return Arrays.stream(values())
                .filter(m -> m.httpMethod.equals(httpMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("잘못된 HttpMethod 입니다. value = %s", httpMethod)));
    }

}
