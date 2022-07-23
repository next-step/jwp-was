package webserver.header.exception;

public class InvalidRequestParamsException extends RuntimeException {
    public InvalidRequestParamsException(String message) {
        super(message);
    }
}
