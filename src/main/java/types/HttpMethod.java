package types;

import exception.HttpNotFoundException;

import java.util.Arrays;

public enum HttpMethod {

    GET, POST;

    public static HttpMethod find(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> method.toUpperCase().equals(httpMethod.name()))
                .findAny()
                .orElseThrow(HttpNotFoundException::new);
    }
}
