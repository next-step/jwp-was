package exception;

public class UnsupportedMethodException extends RuntimeException {
    public UnsupportedMethodException() {
        this("unsupported method");
    }

    public UnsupportedMethodException(String message) {
        super(message);
    }
}
