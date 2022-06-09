package webserver;

import exception.IllegalHttpMethodException;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod from(String method) {
        try {
            return valueOf(method.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalHttpMethodException(method);
        }
    }
}
