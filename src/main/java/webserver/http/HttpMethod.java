package webserver.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    private static final Map<String, HttpMethod> mappings;

    static {
        mappings = Arrays.stream(values())
                .collect(Collectors.toMap(Enum::name, method -> method));
    }

    public static HttpMethod resolve(String method) {
        return mappings.get(method);
    }
}
