package webserver.http;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod of(String method) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));
    }
}
