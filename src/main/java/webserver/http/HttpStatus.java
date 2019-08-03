package webserver.http;

public enum HttpStatus {

    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private static final String FORMAT = "%d %s";

    private final int code;
    private final String reasonPhrase;

    HttpStatus(final int code,
               final String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, code, reasonPhrase);
    }
}
