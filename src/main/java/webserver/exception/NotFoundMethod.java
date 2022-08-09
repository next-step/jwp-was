package webserver.exception;

public class NotFoundMethod extends RuntimeException{
    public NotFoundMethod(String msg) {
        super(msg);
    }
}
