package webserver.exceptions;

import http.request.HttpMethod;

public class MethodNotAllowedException extends WebServerException {
    public MethodNotAllowedException(HttpMethod httpMethod) {
        super(ErrorMessage.METHOD_NOT_ALLOWED, httpMethod.getMethod());
    }
}
