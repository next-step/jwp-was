package http;

/**
 * @author KingCjy
 */
public enum HeaderProperty {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    ACCEPT("Accept"),
    USER_AGENT("User-Agent"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie");

    private String value;

    HeaderProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
