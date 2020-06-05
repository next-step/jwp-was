package utils;

public enum HttpHeader {
    HOST("Host"),
    CONTENT_TYPE("Content-Type");

    private final String originName;

    public String getOriginName() {
        return originName;
    }

    HttpHeader(String originName) {
        this.originName = originName;
    }
}
