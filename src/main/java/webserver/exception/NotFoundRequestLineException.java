package webserver.exception;

public class NotFoundRequestLineException extends RuntimeException{

    public NotFoundRequestLineException(String msg) {
        super(msg);
    }
}
