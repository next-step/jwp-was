package webserver.http.request;

import java.util.Arrays;

public enum Version {
    V_1_1("1.1");

    private String value;

    Version(String value) {
        this.value = value;
    }

    public static Version of(String value) {
        return Arrays.stream(values())
                     .filter(version -> version.value.equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 버전입니다."));
    }
}
