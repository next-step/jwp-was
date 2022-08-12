package webserver.http.header.type;

import webserver.http.header.HeaderKey;

public enum GeneralHeader implements HeaderKey {
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection");

    private final String headerKey;

    GeneralHeader(String headerKey) {
        this.headerKey = headerKey;
    }

    @Override
    public String getHeaderKey() {
        return this.headerKey;
    }
}
