package http;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod findByName(String name) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 HTTP method 이름이 존재하지 않습니다."));
    }

    public boolean isGetRequest() {
        return this == HttpMethod.GET;
    }

    public boolean isPostRequest() {
        return this == HttpMethod.POST;
    }
}
