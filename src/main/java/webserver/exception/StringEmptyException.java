package webserver.exception;

public class StringEmptyException extends RuntimeException{

    public StringEmptyException(String msg) {
        super(msg);
    }
}
