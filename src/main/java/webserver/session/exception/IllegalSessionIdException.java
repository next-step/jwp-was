package webserver.session.exception;

public class IllegalSessionIdException extends RuntimeException {
    private static final String MESSAGE = "%s; 부적절한 sessionId 입니다.";

    public IllegalSessionIdException(String sessionId) {
        super(String.format(MESSAGE, sessionId));
    }
}
