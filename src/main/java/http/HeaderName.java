package http;

import java.util.Arrays;

public enum HeaderName {
    HOST("Host"),
    CONNECTION("Connection"),
    PRAGMA("Pragma"),
    CACHE_CONTROL("Cache-Control"),
    Upgrade_Insecure_Requests("Upgrade-Insecure-Requests"),
    ORIGIN("Origin"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    REFERER("Referer"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    COOKIE("Cookie"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    private final String name;

    HeaderName(String name) {
        this.name = name;
    }

    public static HeaderName findByName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 헤더 정보입니다."));
    }

    public String getName() {
        return name;
    }
}
