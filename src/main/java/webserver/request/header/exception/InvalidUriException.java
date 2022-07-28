package webserver.request.header.exception;

public class InvalidUriException extends RuntimeException{
    public InvalidUriException(String message) {
        super(message);
    }
}
