package webserver.http;

public enum HttpStatusCode {

    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private int code;
    private String message;

    HttpStatusCode(int code, String message) {
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
