package webserver.exceptions;

public class FailedSessionCreateException extends WebServerException {
    public FailedSessionCreateException() {
        super(ErrorMessage.SESSION_NOT_EXIST);
    }
}
