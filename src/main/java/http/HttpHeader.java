package http;

import java.util.HashMap;
import java.util.Map;

public enum HttpHeader {

    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    LOCATION("Location"),
    ICON("image/x-icon");

    private static final Map<String, HttpHeader> headers;

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", HttpHeader.CONTENT_TYPE);
        headers.put("Set-Cookie", HttpHeader.SET_COOKIE);
        headers.put("Location", HttpHeader.LOCATION);
        headers.put("image/x-icon", HttpHeader.ICON);
    }

    private String value;

    HttpHeader(final String value) {
        this.value = value;
    }

    public static HttpHeader of(final String httpHeader) {
        return headers.get(httpHeader);
    }

    public String getValue() {
        return value;
    }

}
