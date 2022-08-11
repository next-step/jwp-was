package webserver.http.header.type;

import webserver.http.header.HeaderKey;

public enum EntityHeader implements HeaderKey {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type");

    private final String headerKey;

    EntityHeader(String headerKey) {
        this.headerKey = headerKey;
    }

    @Override
    public String getHeaderKey() {
        return this.headerKey;
    }
}
