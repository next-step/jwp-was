package webserver.http;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    ;

    private final int code;
    private final String text;

    HttpStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return String.format("%d %s", code, text);
    }
}
