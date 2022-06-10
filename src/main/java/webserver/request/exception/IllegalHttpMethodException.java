package webserver.request.exception;

public class IllegalHttpMethodException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 HTTP Method 입니다.";

    public IllegalHttpMethodException(String method) {
        super(String.format(MESSAGE, method));
    }
}
