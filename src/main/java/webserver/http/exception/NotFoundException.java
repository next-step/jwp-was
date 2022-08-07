package webserver.http.exception;

import webserver.http.HttpStatus;

public class NotFoundException extends RequestProcessException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
