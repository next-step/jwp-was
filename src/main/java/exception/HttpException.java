package exception;

import webserver.http.HttpStatusCode;

public class HttpException extends RuntimeException {

    private HttpStatusCode httpStatusCode;

    public HttpException(HttpStatusCode code) {
        super(code.getMessage());
        this.httpStatusCode = code;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
