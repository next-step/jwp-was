package webserver.http.header;

class InvalidHttpHeaderException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "올바르지 않은 헤더입니다. (입력 값: '%s')";

    InvalidHttpHeaderException(final String input) {
        super(String.format(ERROR_MESSAGE, input));
    }
}
