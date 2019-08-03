package webserver.http;

public enum HeaderKey {

    ACCEPT("Accept"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location")
    ;

    private final String headerKey;

    HeaderKey(final String headerKey) {
        this.headerKey = headerKey;
    }

    @Override
    public String toString() {
        return headerKey;
    }
}
