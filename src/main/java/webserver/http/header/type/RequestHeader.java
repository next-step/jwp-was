package webserver.http.header.type;

import webserver.http.header.HeaderKey;

public enum RequestHeader implements HeaderKey {
    ACCEPT("Accept"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    HOST("Host"),
    SEC_FETCH_SITE("Sec-Fetch-Site"),
    SEC_FETCH_DEST("Sec-Fetch-Dest"),
    SEC_CH_UA("sec-ch-ua"),
    SEC_CH_UA_MOBILE("sec-ch-ua-mobile"),
    SEC_CH_UA_PLATFORM("sec-ch-ua-platform"),
    SEC_FETCH_USER("Sec-Fetch-User"),
    SEC_FETCH_MODE("Sec-Fetch-Mode"),
    USER_AGENT("User-Agent"),
    COOKIE("Cookie"),
    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
    REFERER("Referer"),
    ORIGIN("Origin"),
    PURPOSE("Purpose"),
    IF_NONE_MATCH("If-None-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since");

    private final String headerKey;

    RequestHeader(String headerKey) {
        this.headerKey = headerKey;
    }

    @Override
    public String getHeaderKey() {
        return this.headerKey;
    }
}
