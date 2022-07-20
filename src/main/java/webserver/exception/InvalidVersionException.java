package webserver.exception;

public class InvalidVersionException extends RuntimeException{
    public InvalidVersionException(String message) {
        super(message);
    }
}
