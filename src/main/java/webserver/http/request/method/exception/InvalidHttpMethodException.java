package webserver.http.request.method.exception;

public class InvalidHttpMethodException extends RuntimeException{
    public InvalidHttpMethodException(String message) {
        super(message);
    }
}
