package model.http;

import java.util.Arrays;
import java.util.Optional;

public enum HttpVersion {
    HTTP1_1("HTTP/1.1");

    private String version;

    private HttpVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public static HttpVersion of(String version) {
        return find(version).orElseThrow(() -> new IllegalArgumentException("wrong http version"));
    }

    public static Optional<HttpVersion> find(String version) {
        return Arrays.stream(values())
                .filter(httpVersion -> httpVersion.getVersion().equals(version))
                .findFirst();
    }
}
