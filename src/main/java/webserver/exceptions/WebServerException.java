package webserver.exceptions;

import http.response.StatusCode;

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

    public StatusCode getStatusCode() {
        return errorMessage.getStatusCode();
    }

}
