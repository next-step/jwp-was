package webserver;

import java.util.Arrays;

public enum HttpProtocol {
    HTTP;

    public static HttpProtocol of(String httpProtocolValue) {
        return Arrays.stream(values())
                .filter(httpProtocol -> httpProtocolValue.equals(httpProtocol.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("잘못된 HTTP protocol httpProtocolValue:[%s]", httpProtocolValue)));
    }
}
