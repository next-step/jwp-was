package webserver;

import java.util.Arrays;
import java.util.Objects;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod resolve(String method) {
        if (Objects.isNull(method)) {
            return null;
        }

        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findAny()
                .orElse(null);
    }
}
