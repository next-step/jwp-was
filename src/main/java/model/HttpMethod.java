package model;

import java.util.Arrays;
import java.util.Optional;

public enum HttpMethod {
    GET, POST
    ;

    public static Optional<HttpMethod> of(String method) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findAny();
    }
}
