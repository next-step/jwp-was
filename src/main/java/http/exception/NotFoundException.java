package http.exception;

import http.HttpStatus;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
