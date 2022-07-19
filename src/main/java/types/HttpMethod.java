package types;

import exception.HttpNotFoundException;

import java.util.Arrays;

public enum HttpMethod {

    GET, POST;

    public static HttpMethod getMethod(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> method.toUpperCase().equals(httpMethod.name()))
                .findAny()
                .orElseThrow(() -> new HttpNotFoundException(new IllegalArgumentException()));
    }
}
