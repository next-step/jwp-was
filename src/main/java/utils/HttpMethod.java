package utils;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH");

    String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static boolean checkHttpMethod(String requestMethod) {
        return Arrays.stream(HttpMethod.values())
                .anyMatch(method -> requestMethod.equals(method.name));
    }
}

