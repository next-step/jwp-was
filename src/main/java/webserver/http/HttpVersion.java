package webserver.http;

import java.util.Arrays;

public enum HttpVersion {
    ONE("1.0"), ONE_POINT_ONE("1.1"),
    ;

    public String get() {
        return httpVersionValue;
    }

    private final String httpVersionValue;

    HttpVersion(String httpVersionValue) {
        this.httpVersionValue = httpVersionValue;
    }

    public static HttpVersion of(String httpVersionValue) {
        return Arrays.stream(values())
                .filter(httpVersion -> httpVersionValue.equals(httpVersion.httpVersionValue))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("잘못된 HTTP version httpVersionValue:[%s]", httpVersionValue)));
    }
}
