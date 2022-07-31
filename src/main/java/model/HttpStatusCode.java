package model;

public enum HttpStatusCode {
    OK(200, "OK"),
    CREATED(201,"CREATED"),
    INTERNAL_SERVER_ERROR(500,"INTERNAL SERVER ERROR")
    ;

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
