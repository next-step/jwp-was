package webserver.http;

import java.util.Arrays;

public enum HttpHeaders {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie"),
    HOST("Host"),
    ACCEPT("Accept"),
    ACCEPT_LANGUAGE("Accept-Language"),
    CONNECTION("Connection"),
    USER_AGENT("User-Agent")
    ;

    private String header;

    HttpHeaders(String header) {
        this.header = header;
    }

    public static HttpHeaders of(String value) {
        return Arrays.stream(values())
                .filter( httpHeaders -> httpHeaders.equals(value))
                .findAny()
                .orElseThrow( () -> new IllegalArgumentException("존재하지 않는 HttpHeaders 입니다. value=" + value));
    }

    public String getHeader() {
        return header;
    }

    public boolean equals(String header) {
        return this.header.equals(header);
    }
}
