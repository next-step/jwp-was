package webserver.http.request;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod from(String value) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("HTTP 메소드를 파싱할 수 없습니다."));
    }

    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(HttpMethod::value)
                .collect(Collectors.toList());
    }

    public String value() {
        return value;
    }
}
