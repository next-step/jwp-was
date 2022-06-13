package webserver.request;

import webserver.request.exception.IllegalHttpMethodException;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static RequestMethod from(String method) {
        try {
            return valueOf(method.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalHttpMethodException(method);
        }
    }
}
