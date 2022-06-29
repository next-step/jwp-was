package webserver.common.exception;

public class IllegalCookieKeyException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 Cookie 의 키입니다.";

    public IllegalCookieKeyException(String key) {
        super(String.format(MESSAGE, key));
    }
}
