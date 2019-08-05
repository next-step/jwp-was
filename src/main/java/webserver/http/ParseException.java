package webserver.http;

public class ParseException extends RuntimeException {
    public ParseException() {
    }

    public ParseException(final String message) {
        super(message);
    }
}
