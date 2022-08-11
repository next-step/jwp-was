package webserver.http.header.type;

import webserver.http.header.HeaderKey;

public enum ResponseHeader implements HeaderKey {
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie");

    private final String headerKey;

    ResponseHeader(String headerKey) {
        this.headerKey = headerKey;
    }

    @Override
    public String getHeaderKey() {
        return this.headerKey;
    }
}
