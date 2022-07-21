package webserver.domain;

import java.util.stream.Stream;

public enum Version {
    ONE("1.0"), ONE_DOT_ONE("1.1"), TWO("2");

    private final String value;

    Version(String value) {
        this.value = value;
    }

    public static Version from(String version) {
        return Stream.of(values())
                .filter(v-> v.value.equals(version))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
