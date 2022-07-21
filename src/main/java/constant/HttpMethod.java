package constant;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,
    PATCH,
    DELETE,
    PUT;

    public static HttpMethod of(String httpmethod) {
        return Arrays.stream(values())
                .filter(method -> method.name().equals(httpmethod))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Message.NOT_ALLOW_HTTP_METHOD.getValue()));
    }

}
