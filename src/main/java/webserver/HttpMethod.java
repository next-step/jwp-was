package webserver;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), PATCH("PATCH"), DELETE("DELETE");

    private String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod of(String method) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.value.equals(method))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 HTTP Method입니다."));
    }
}
