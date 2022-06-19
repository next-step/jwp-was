package webserver;

public enum HttpStatus {
    OK(200, "OK"),

    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String message;

    HttpStatus(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%d %s", code, message);
    }
}