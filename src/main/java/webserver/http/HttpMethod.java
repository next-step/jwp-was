package webserver.http;

import webserver.http.exception.MethodNotAllowedException;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), PATCH("PATCH"), DELETE("DELETE");

    private String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod of(String method) {
        try {
            return valueOf(method.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new MethodNotAllowedException("허용하지 않은 HTTP Method입니다.");
        }
    }
}
