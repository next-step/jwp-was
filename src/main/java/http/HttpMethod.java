package http;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
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

    public static String resolve(String code) {
        if (Objects.isNull(code)) {
            return null;
        }

        return Optional.ofNullable(httpMethodMap.get(code))
            .map(HttpMethod::name)
            .orElseThrow(IllegalArgumentException::new);
    }
}
