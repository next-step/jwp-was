package webserver.http.service.exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String message) {
        super(message);
    }
}
