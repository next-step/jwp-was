package webserver.http.request;

class InvalidRequestURLException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "올바르지 않은 요청 URL 값 입니다. (입력 값: '%s')";

    InvalidRequestURLException(final String input) {
        super(String.format(ERROR_MESSAGE, input));
    }
}
