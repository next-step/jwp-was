package http;

import java.security.InvalidParameterException;

public enum Method {
    GET, POST, PUT, PATCH, HEAD, OPTION,
    ;

    Method() {
    }

    public static Method find(String method) {
        for (Method value : Method.values()) {
            if (value.name().equalsIgnoreCase(method)) {
                return value;
            }
        }

        throw new InvalidParameterException();
    }
}
