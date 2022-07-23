package webserver.domain;

public enum HttpStatus {
    OK("200 OK"),
    CREATED("201 Created"),
    BAD_REQUEST("400 Bad Request"),
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 Not Found");

    private final String value;

    HttpStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}