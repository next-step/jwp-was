package http.exception;

import http.HttpStatus;

public class MethodNotAllowedException extends HttpException {
    public MethodNotAllowedException() {
        super(HttpStatus.METHOD_NOT_ALLOWED);
    }
}