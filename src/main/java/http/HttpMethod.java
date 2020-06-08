package http;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public enum HttpMethod {
    GET,
    POST,
    ;

    private static final Map<String, HttpMethod> httpMethodMap = initHttpMethodMap();

    private static Map<String, HttpMethod> initHttpMethodMap() {
        return Arrays.stream(values())
            .collect(toMap(HttpMethod::name, entry -> entry));
    }

    public static HttpMethod resolve(String method) {
        return Optional.ofNullable(method)
            .map(httpMethodMap::get)
            .orElse(null);
    }

    public boolean matches(String method) {
        return (this == resolve(method));
    }
}
