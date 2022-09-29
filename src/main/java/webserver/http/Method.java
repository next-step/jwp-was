package webserver.http;

import exception.IllegalProtocolMethodException;

import java.util.Arrays;

public enum Method {
    GET, POST;

    public static Method from(String method) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(method))
                .findFirst()
                .orElseThrow(() -> new IllegalProtocolMethodException(method));
    }
}
