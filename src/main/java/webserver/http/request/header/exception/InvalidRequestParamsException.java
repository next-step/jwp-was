package webserver.http.request.header.exception;

public class InvalidRequestParamsException extends RuntimeException {
    public InvalidRequestParamsException(String message) {
        super(message);
    }
}
