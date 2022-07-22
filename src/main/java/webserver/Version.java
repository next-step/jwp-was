package webserver;

import java.util.Arrays;

public enum Version {
    ONE("1.0"),
    ONE_ONE("1.1");

    private String figure;

    Version(String version) {
        this.figure = version;
    }

    public static Version from(String figure) {
        return Arrays.stream(values())
                .filter(v -> v.figure.equals(figure))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 HTTP Version 입니다."));
    }
}
