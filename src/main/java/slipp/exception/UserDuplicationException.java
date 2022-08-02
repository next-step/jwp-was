package slipp.exception;

import webserver.http.domain.exception.BadRequestException;

public class UserDuplicationException extends BadRequestException {
    public UserDuplicationException(String message) {
        super(message);
    }
}
