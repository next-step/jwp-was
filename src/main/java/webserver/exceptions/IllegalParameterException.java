package webserver.exceptions;

public class IllegalParameterException extends WebServerException {
    public IllegalParameterException(String parameter) {
        super(ErrorMessage.ILLEGAL_PARAMETER, parameter);
    }
}
