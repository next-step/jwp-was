package exception;

public class InvalidCookieException extends RuntimeException {
    public InvalidCookieException(String message) {
        super(message);
    }
}
