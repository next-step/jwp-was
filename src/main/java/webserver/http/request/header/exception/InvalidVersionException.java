package webserver.http.request.header.exception;

public class InvalidVersionException extends RuntimeException{
    public InvalidVersionException(String message) {
        super(message);
    }
}
