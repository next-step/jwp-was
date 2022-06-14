package webserver.request.exception;

public class IllegalRequestBodyException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 RequestBody 입니다.";

    public IllegalRequestBodyException(String body) {
        super(String.format(MESSAGE, body));
    }
}
