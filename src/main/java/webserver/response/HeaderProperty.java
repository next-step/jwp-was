package webserver.response;

public enum HeaderProperty {

    ACCEPT("Accept"),
    LOCATION("Location"),
    CONTENT_LENGTH("Content-Length"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie"),
    CONTENT_TYPE("Content-Type");

    private String headerName;

    HeaderProperty(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
