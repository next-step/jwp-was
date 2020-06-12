package webserver.exceptions;

public class StatusCodeNotFoundException extends WebServerException {
    public StatusCodeNotFoundException() {
        super(ErrorMessage.STATUS_CODE_NOT_FOUND);
    }
}
