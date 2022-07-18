package exception;

public class NotExistHttpMethodException extends RuntimeException {
    public NotExistHttpMethodException() {
        super();
    }

    public NotExistHttpMethodException(String message) {
        super(message);
    }

    public NotExistHttpMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistHttpMethodException(Throwable cause) {
        super(cause);
    }

    protected NotExistHttpMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
