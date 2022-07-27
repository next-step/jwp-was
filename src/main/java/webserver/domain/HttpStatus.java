package webserver.domain;

public enum HttpStatus {
    OK("200 OK"),
    CREATED("201 Created"),
    FOUND("302 Found"),
    BAD_REQUEST("400 Bad Request"),
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 Not Found"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error");

    public static final String HTTP_1_1 = "HTTP/1.1 ";

    private final String value;

    HttpStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return HTTP_1_1 + value;
    }
}
