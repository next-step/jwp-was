package http.exceptions;

public class UnsupportedHttpMethodException extends RuntimeException {
    public UnsupportedHttpMethodException(String message) {
        super(message);
    }
}
