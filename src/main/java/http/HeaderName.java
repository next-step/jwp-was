package http;

public enum HeaderName {

    REQUEST_COOKIE("Cookie"),
    RESPONSE_COOKIE("Set-Cookie"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    private String key;

    HeaderName(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
