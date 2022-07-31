package webserver.http.domain.response;

public enum StatusCode {
    OK("OK", 200),
    FOUND("Found", 302),
    BAD_REQUEST("Bad Request", 400),
    NOT_FOUND("Not Found", 404),
    INTERNAL_ERROR("Internal Server Error", 500),
    ;

    private final String message;
    private final int code;

    StatusCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
