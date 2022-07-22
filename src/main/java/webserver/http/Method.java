package webserver.http;

import exception.IllegalProtocolMethodException;

import java.util.Arrays;

public enum Method {
    GET("GET"), POST("POST");

    private final String label;

    Method(String label) {
        this.label = label;
    }

    public static Method from(String method) {
        return Arrays.stream(values())
                .filter(it -> it.label.equalsIgnoreCase(method))
                .findFirst()
                .orElseThrow(() -> new IllegalProtocolMethodException(method));
    }

    public String getLabel() {
        return label;
    }
}
