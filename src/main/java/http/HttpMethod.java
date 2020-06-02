package http;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private static final Map<String, HttpMethod> httpMethodMap = initHttpMethodMap();

    private static Map<String, HttpMethod> initHttpMethodMap() {
        return Arrays.stream(values())
                .collect(toMap(HttpMethod::name, entry -> entry));
    }

    public static HttpMethod resolve(String code) {
        if (Objects.isNull(code)) {
            return null;
        }

        return httpMethodMap.get(code);
    }
}
