package webserver.request.method;

import java.util.Arrays;

import webserver.request.method.exception.InvalidHttpMethodException;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private final String requestMethod;

    HttpMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public static HttpMethod from(String requestMethod) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.requestMethod.equals(requestMethod))
                .findAny()
                .orElseThrow(() -> new InvalidHttpMethodException("유효하지 않은 HttpMethod 입니다."));
    }

    public static boolean isGet(String httpMethod) {
        return GET.name().equals(httpMethod);
    }

    public static boolean isPost(String httpMethod) {
        return POST.name().equals(httpMethod);
    }
}
