package webserver.http;

class InvalidQueryStringValueException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "올바르지 않은 쿼리스트링 값 입니다. (입력 값: '%s')";

    InvalidQueryStringValueException(final String input) {
        super(String.format(ERROR_MESSAGE, input));
    }
}
