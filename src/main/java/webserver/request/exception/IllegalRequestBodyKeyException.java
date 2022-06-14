package webserver.request.exception;

public class IllegalRequestBodyKeyException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 RequestBody 의 키입니다.";

    public IllegalRequestBodyKeyException(String key) {
        super(String.format(MESSAGE, key));
    }
}
