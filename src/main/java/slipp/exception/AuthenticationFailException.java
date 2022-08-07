package slipp.exception;

import webserver.http.domain.exception.BadRequestException;

public class AuthenticationFailException extends BadRequestException {
    public AuthenticationFailException(String message) {
        super(message);
    }
}
