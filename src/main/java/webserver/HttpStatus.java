package webserver;

public enum HttpStatus {
    OK(200, "OK");

    private final int code;
    private final String message;

    HttpStatus(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}