package webserver.exceptions;

public class WebServerException extends RuntimeException {

    private ErrorMessage errorMessage;

    public WebServerException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public WebServerException(ErrorMessage errorMessage, String value) {
        super(errorMessage.getMessage() + value);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
