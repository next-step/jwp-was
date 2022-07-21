package webserver.domain;

import exception.ResourceNotFound;

import java.util.Arrays;

public enum HttpVersion {

    VER_1_0("1.0"),
    VER_1_1("1.1"),
    VER_2_0("2.0");

    private final String name;

    HttpVersion(String name) {
        this.name = name;
    }

    public static HttpVersion from(String versionName) {
        return Arrays.stream(values())
                .filter(value -> versionName.equals(value.name))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound());
    }

    public String getName() {
        return name;
    }
}
