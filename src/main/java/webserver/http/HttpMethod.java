package webserver.http;

import webserver.http.exception.NotImplementedException;

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
                .orElseThrow(() -> new NotImplementedException("허용하지 않은 HTTP Method입니다."));
    }
}
