package webserver.exceptions;

public class ResponseSequelizerNotImplementedException extends WebServerException {
    public ResponseSequelizerNotImplementedException() {
        super(ErrorMessage.METHOD_NOT_ALLOWED);
    }
}
