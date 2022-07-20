package request;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), PATCH("PATCH"), DELETE("DELETE");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod from(String request) {
        return Arrays.stream(values())
            .filter(it -> it.value.equals(request))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 HttpMethod 요청입니다."));
    }
}
