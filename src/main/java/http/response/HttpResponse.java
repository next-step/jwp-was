package http.response;

import http.HttpEntity;
import http.HttpStatus;

public class HttpResponse {
    private final HttpStatus httpStatus;
    private final HttpEntity httpEntity;

    public HttpResponse(HttpStatus httpStatus) {
        this(httpStatus, null);
    }

    public HttpResponse(HttpStatus httpStatus, HttpEntity httpEntity) {
        this.httpStatus = httpStatus;
        this.httpEntity = httpEntity;
    }
}
