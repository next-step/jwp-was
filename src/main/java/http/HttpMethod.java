package http;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HttpMethod {
    GET,
    POST;

    private static final Map<String, HttpMethod> HTTP_METHODS =
            Arrays.stream(values()).collect(Collectors.toMap(HttpMethod::getName, Function.identity()));

    public static HttpMethod of(final String methodString) {
        if (!HTTP_METHODS.containsKey(methodString)) {
            throw new IllegalArgumentException("Unknown method type : " + methodString);
        }

        return HTTP_METHODS.get(methodString);
    }

    private String getName() {
        return this.name();
    }
}
