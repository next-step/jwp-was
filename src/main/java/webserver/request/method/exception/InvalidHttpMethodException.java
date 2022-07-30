package webserver.request.method.exception;

public class InvalidHttpMethodException extends RuntimeException{
    public InvalidHttpMethodException(String message) {
        super(message);
    }
}
