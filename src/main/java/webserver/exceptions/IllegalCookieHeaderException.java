package webserver.exceptions;

public class IllegalCookieHeaderException extends WebServerException {
    public IllegalCookieHeaderException() {
        super(ErrorMessage.ILLEGAL_COOKIE_HEADER);
    }
}
