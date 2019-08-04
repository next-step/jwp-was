package webserver.http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    private static Map<String, HttpMethod> httpMethods;
    static {
        httpMethods = new HashMap<>();
        for (HttpMethod method : values()) {
            httpMethods.put(method.name(), method);
        }
    }

    public static HttpMethod find(String method) {
        return Optional.ofNullable(httpMethods.get(method))
                .orElseThrow(() -> new IllegalArgumentException("Not supported method"));
    }
}
