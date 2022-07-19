package exception;

public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException(Throwable cause) {
        super("http method not found !!", cause);
    }

}
