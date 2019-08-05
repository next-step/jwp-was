package exception;

import webserver.StatusCode;

public class HttpException extends RuntimeException {

    private StatusCode statusCode;

    public HttpException(StatusCode code) {
        super(code.getMessage());
        this.statusCode = code;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
