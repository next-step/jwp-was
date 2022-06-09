package webserver.request.exception;

public class IllegalRequestLineException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 RequestLine 입니다.";

    public IllegalRequestLineException(String requestLine) {
        super(String.format(MESSAGE, requestLine));
    }
}
