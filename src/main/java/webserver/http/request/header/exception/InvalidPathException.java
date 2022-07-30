package webserver.http.request.header.exception;

public class InvalidPathException extends RuntimeException{
    public InvalidPathException(String message) {
        super(message);
    }
}
