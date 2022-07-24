package webserver.http;

import java.util.Arrays;
import java.util.Objects;

public enum Version {

    ZERO_NINE("0.9"),
    ONE("1.0"),
    ONE_ONE("1.1"),
    TWO("2.0"),
    THREE("3.0");

    private final String version;

    Version(String version) {
        this.version = version;
    }

    public static Version from(String value) {
        return Arrays.stream(values())
                .filter(it -> Objects.equals(it.version, value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getVersion() {
        return version;
    }
}
