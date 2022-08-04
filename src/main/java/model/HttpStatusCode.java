package model;

public enum HttpStatusCode {
    OK(200, "OK"),
    CREATED(201, "CREATED"),
    FOUND(302, "FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    NOT_FOUND(404, "NOT FOUND URL");

    private int code;
    private String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getHttpResponseCode() {
        return String.join(" ", String.valueOf(code), message);
    }
}
