package webserver;

public enum StatusCode {

    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    ;

    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
