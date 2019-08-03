package webserver.http.request;

class InvalidRequestPathException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "올바르지 않은 요청 경로 입니다. (입력 값: '%s')";

    InvalidRequestPathException(final String input) {
        super(String.format(ERROR_MESSAGE, input));
    }
}
