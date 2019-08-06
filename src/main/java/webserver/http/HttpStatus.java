package webserver.http;

public enum HttpStatus {
    OK(200, "OK"),
    REDIRECT(302, "Found");

    private int code;
    private String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
