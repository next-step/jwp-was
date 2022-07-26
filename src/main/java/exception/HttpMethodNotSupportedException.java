package exception;

import webserver.http.HttpMethod;

public class HttpMethodNotSupportedException extends RuntimeException {

    private static final String MESSAGE = "해당 HTTP 메서드는 지원하지 않습니다 : %s";

    public HttpMethodNotSupportedException(HttpMethod method) {
        super(String.format(MESSAGE, method));
    }
}
