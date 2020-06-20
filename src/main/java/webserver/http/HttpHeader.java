package webserver.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HttpHeader {
    CONTENT_TYPE("Content-Type", ";"),
    CONTENT_LENGTH("Content-Length"),
    ACCEPT_ENCODING("Accept-Encoding", ", "),
    ACCEPT_LANGUAGE("Accept-Language", ", "),
    ACCEPT("Accept"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie", ";"),
    COOKIE("Cookie"),
    HOST("Host"),
    CONNECTION("Connection"),
    VIA("Via"),
    USER_AGENT("User-Agent"),
    CACHE_CONTROL("Cache-Control"),
    ORIGIN("Origin"),
    NOT_SUPPORTED("Not Supported Header Type");

    private String name;
    private String delimiter;

    HttpHeader(String name) {
        this.name = name;
        this.delimiter = "";
    }

    public static HttpHeader of(String headerName) {
        return Arrays.stream(HttpHeader.values())
                .filter(httpHeader -> httpHeader.getName().equals(headerName))
                .findFirst()
                .orElse(NOT_SUPPORTED);
    }
}
