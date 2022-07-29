package webserver.response;

public enum StatusCode {

    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int code;
    private final String reasonPhrase;

    StatusCode(final int code, final String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public String message() {
        return code + " " + reasonPhrase;
    }
}
