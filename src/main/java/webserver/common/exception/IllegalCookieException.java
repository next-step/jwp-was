package webserver.common.exception;

public class IllegalCookieException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 Cookie 입니다.";

    public IllegalCookieException(String cookie) {
        super(String.format(MESSAGE, cookie));
    }
}
