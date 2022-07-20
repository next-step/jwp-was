package webserver.http;

import exception.IllegalProtocolVersionException;

import java.util.Arrays;

public enum Version {
    VERSION1_1("1.1");

    private final String label;

    Version(String label) {
        this.label = label;
    }

    public static Version from(String version) {
        return Arrays.stream(values())
                .filter(it -> it.label.equalsIgnoreCase(version))
                .findFirst()
                .orElseThrow(() -> new IllegalProtocolVersionException(version));
    }

    public String getLabel() {
        return label;
    }
}
