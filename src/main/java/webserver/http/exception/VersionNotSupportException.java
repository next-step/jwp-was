package webserver.http.exception;

import webserver.http.HttpStatus;

public class VersionNotSupportException extends RequestProcessException {

    public VersionNotSupportException(String message) {
        super(message, HttpStatus.VERSION_NOT_SUPPORT);
    }
}
