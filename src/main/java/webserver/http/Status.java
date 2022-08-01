package webserver.http;

public enum Status {

    OK("200", "OK"),

    FOUND("302", "FOUND");

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
