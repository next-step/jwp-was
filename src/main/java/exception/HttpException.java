package exception;

import webserver.http.HttpStatusCode;

public class HttpException extends RuntimeException {

    private HttpStatusCode httpStatusCode;

    public HttpException() {
        this.httpStatusCode = HttpStatusCode.BAD_REQUEST;
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(HttpStatusCode code) {
        super(code.getMessage());
        this.httpStatusCode = code;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
