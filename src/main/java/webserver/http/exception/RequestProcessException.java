package webserver.http.exception;

import webserver.http.HttpStatus;

public class RequestProcessException extends RuntimeException {

    private HttpStatus status;

    public RequestProcessException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public RequestProcessException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
