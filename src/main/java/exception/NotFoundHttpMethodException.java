package exception;

public class NotFoundHttpMethodException extends RuntimeException {
    public NotFoundHttpMethodException(String message) {
        super(message);
    }
}
