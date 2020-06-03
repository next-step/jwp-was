package http.exceptions;

public class UnsupportedHttpMethodException extends RuntimeException {
    public UnsupportedHttpMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
