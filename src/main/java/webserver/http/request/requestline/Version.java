package webserver.http.request.requestline;

import java.util.Arrays;

public enum Version {
    ONE(1.0),
    ONE_ONE(1.1),
    TWO(2.0);

    private final double version;

    Version(double version) {
        this.version = version;
    }

    public static Version valueOfVersion(String version) {
        return Arrays.stream(Version.values())
                .filter(versionValue -> versionValue.version == Double.parseDouble(version))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("전달받은 version 은 존재하지 않는 버전 입니다. 전달 받은 version = %f", version)));
    }

    public double version() {
        return this.version;
    }
}
