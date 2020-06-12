package webserver.exceptions;

public class IllegalCookieHeaderException extends WebServerException {
    public IllegalCookieHeaderException(String cookie) {
        super(ErrorMessage.ILLEGAL_COOKIE_HEADER, cookie);
    }
}
