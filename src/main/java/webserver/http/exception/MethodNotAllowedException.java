package webserver.http.exception;

import webserver.http.HttpStatus;

public class MethodNotAllowedException extends RequestProcessException {

    public MethodNotAllowedException(String message) {
        super(message, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
