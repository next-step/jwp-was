package webserver.http;

import exception.IllegalProtocolTypeException;

import java.util.Arrays;

public enum Type {
    HTTP("HTTP");

    private final String label;

    Type(String label) {
        this.label = label;
    }

    public static Type from(String type) {
        return Arrays.stream(values())
                .filter(it -> it.label.equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalProtocolTypeException(type));
    }

    public String getLabel() {
        return label;
    }
}
