package webserver.httprequest.requestline;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod of(String methodValue) {
        return Arrays.stream(values())
                .filter(httpMethod -> methodValue.equals(httpMethod.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("유효하지 않은 HTTP method입니다. HttpMethod: [%s]", methodValue)));
    }
}
