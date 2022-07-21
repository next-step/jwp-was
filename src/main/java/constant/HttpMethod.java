package constant;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,

    UNKNOWN;

    public static HttpMethod of(String httpmethod) {
        return Arrays.stream(values())
                .filter(method -> method.name().equals(httpmethod))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
