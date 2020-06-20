package http.common;

public enum HttpHeader {
    CONTENT_TYPE("Content-Type"),
    SET_COOKIE("Set-Cookie"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    COOKIE("Cookie");

    private final String text;

    HttpHeader(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
