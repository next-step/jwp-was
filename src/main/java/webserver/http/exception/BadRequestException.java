package webserver.http.exception;

import webserver.http.HttpStatus;

public class BadRequestException extends RequestProcessException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
