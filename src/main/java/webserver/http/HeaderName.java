package webserver.http;

public enum HeaderName {

    ACCEPT("Accept"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    COOKIE("Cookie")
    ;

    private final String headerName;

    HeaderName(final String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String toString() {
        return headerName;
    }
}
