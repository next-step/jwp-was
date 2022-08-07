package webserver.http;

public enum Status {

    OK("200", "OK"),

    FOUND("302", "FOUND"),

    NOT_FOUND("404", "NOT_FOUND"),

    INTERNAL_SERVER_ERROR("500", "INTERNAL_SERVER_ERROR");

    private final String code;

    private final String reason;

    Status(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    String getMessage() {
        return code + " " + reason;
    }
}
