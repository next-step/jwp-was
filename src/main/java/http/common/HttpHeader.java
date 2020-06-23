package http.common;

public enum HttpHeader {
    // For Common
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    CONNECTION("Connection"),

    // For Response
    SET_COOKIE("Set-Cookie"),

    // For Request
    HOST("Host"),
    ACCEPT("Accept"),
    COOKIE("Cookie");

    private final String text;

    HttpHeader(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
