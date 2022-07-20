package exception;

public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException() {
        super("http method not found !!", new IllegalArgumentException());
    }

}
