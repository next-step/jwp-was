package http.exception;

import http.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
