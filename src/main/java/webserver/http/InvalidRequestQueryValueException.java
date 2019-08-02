package webserver.http;

class InvalidRequestQueryValueException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "올바르지 않은 요청 쿼리 값 입니다. (입력 값: '%s')";

    InvalidRequestQueryValueException(final String input) {
        super(String.format(ERROR_MESSAGE, input));
    }
}
