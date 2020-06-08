package utils;

public enum HttpHeader {
    HOST("Host"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    COOKIE("Cookie")
    ;

    private final String originName;

    public String getOriginName() {
        return originName;
    }

    HttpHeader(String originName) {
        this.originName = originName;
    }
}
