package webserver.request;

import exception.UnSupportedHttpMethodException;
import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod of(String name) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new UnSupportedHttpMethodException(name));
    }
}
