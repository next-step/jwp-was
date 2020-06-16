package webserver.exceptions;

public class SessionNotExistException extends WebServerException {
    public SessionNotExistException() {
        super(ErrorMessage.SESSION_NOT_EXIST);
    }
}
