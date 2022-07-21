package exception;

public class IllegalHttpRequestException extends RuntimeException {
    public IllegalHttpRequestException(String message) {
        super(message);
    }
}
