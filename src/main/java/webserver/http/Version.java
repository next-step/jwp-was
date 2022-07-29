package webserver.http;

import java.util.Arrays;

public enum Version {
    V1_1("1.1"),
    V2_0("2.0")
    ;

    private final String version;

    Version(String version) {
        this.version = version;
    }

    public static Version fromString(String targetVersion) {
        return Arrays.stream(values())
                .filter(v -> v.version.equals(targetVersion))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
