package webserver.http;

import exception.IllegalProtocolTypeException;

import java.util.Arrays;

public enum Type {
    HTTP;

    public static Type from(String type) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalProtocolTypeException(type));
    }
}
