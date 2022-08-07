package webserver.http;

import java.util.Arrays;

public enum HeaderKey {
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    ACCEPT("Accept"),
    CONNECTION("Connection"),
    USER_AGENT("User-Agent"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    HOST("Host"),
    ACCEPT_ENCODING("Accept-Encoding"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    SEC_CH_UA("sec-ch-ua"),
    SEC_CH_UA_MOBILE("sec-ch-ua-mobile"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    SEC_CH_UA_PLATFORM("sec-ch-ua-platform"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    ACCEPT_LANGUAGE("Accept-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    LOCATION("Location"),
    CACHE_CONTROL("Cache-Control"),
    REFERER("Referer"),
    ORIGIN("Origin"),
    PURPOSE("Purpose")
    ;


    private final String headerKey;
    HeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public static HeaderKey valueOfKey(String keyString) {
        return Arrays.stream(HeaderKey.values())
                .filter(key -> key.headerKey.equals(keyString))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("전달받은 header key 는 존재하지 않는 헤더의 키 값 입니다. 전달 받은 key = %s", keyString)));
    }

    public String getHeaderKey() {
        return headerKey;
    }
}
