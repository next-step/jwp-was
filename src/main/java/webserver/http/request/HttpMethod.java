package webserver.http.request;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static HttpMethod lookup(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name().equals(method))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static boolean isPOST(HttpMethod ohterMethod) {
        return POST == ohterMethod;
    }

    public static boolean isGET(HttpMethod ohterMethod) {
        return GET == ohterMethod;
    }
}
