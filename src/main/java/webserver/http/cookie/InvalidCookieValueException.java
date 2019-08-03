package webserver.http.cookie;

class InvalidCookieValueException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "올바르지 않은 쿠키 값 입니다. (입력 값: '%s')";

    InvalidCookieValueException(final String input) {
        super(String.format(ERROR_MESSAGE, input));
    }
}
