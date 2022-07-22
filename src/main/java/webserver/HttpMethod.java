package webserver;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    HttpMethod(String methodName) {}

    public static HttpMethod from(String methodName) {
        return Arrays.stream(values())
                .filter(method -> method.name().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 HTTP method 입니다."));
    }
}
