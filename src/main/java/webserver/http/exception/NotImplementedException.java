package webserver.http.exception;

import webserver.http.HttpStatus;

public class NotImplementedException extends RequestProcessException {

    public NotImplementedException(String message) {
        super(message, HttpStatus.NOT_IMPLEMENTED);
    }
}
