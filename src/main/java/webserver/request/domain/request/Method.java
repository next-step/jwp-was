package webserver.request.domain.request;

import webserver.exception.NotFoundMethod;

import java.util.Arrays;

public enum Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String value;

    Method(String method) {
        value = method;
    }

    public static Method from(String method) {
        return Arrays.stream(values())
                .filter(n -> n.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new NotFoundMethod("지원하지 않는 method입니다."));
    }

    public boolean isPost() {
        return this == Method.POST;
    }

    public boolean isGet() {
        return this == Method.GET;
    }
}
