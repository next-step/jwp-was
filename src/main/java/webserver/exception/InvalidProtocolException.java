package webserver.exception;

public class InvalidProtocolException extends RuntimeException{
    public InvalidProtocolException(String message) {
        super(message);
    }
}
