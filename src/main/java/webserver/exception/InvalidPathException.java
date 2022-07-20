package webserver.exception;

public class InvalidPathException extends RuntimeException{
    public InvalidPathException(String message) {
        super(message);
    }
}
