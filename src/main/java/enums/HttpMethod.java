package enums;

import java.util.Arrays;

public enum HttpMethod {
    GET
    , POST
    , PUT
    , PATCH
    , DELETE
    , OPTIONS
    , HEAD
    , TRACE;

    public static HttpMethod parse(String httpMethod) {

        return Arrays.stream(HttpMethod.values())
                .filter(method -> method.name().equalsIgnoreCase(httpMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 httpMethod가 아님."));
    }
}
