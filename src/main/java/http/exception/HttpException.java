package http.exception;

import http.HttpStatus;
import lombok.Getter;

public class HttpException extends RuntimeException {
    @Getter
    protected final HttpStatus httpStatus;

    public HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatusCode() {
        return this.httpStatus.value();
    }

    public String getHttpStatusReasonPhrase() {
        return this.httpStatus.getReasonPhrase();
    }
}
