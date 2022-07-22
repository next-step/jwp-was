package webserver.exception;

import webserver.response.HttpStatusCode;
import webserver.response.ResponseHeader;

public final class ApiException extends RuntimeException {

    private final HttpStatusCode code;
    private final ResponseHeader header;

    public ApiException(HttpStatusCode code, ResponseHeader header) {
        super(code.toString());
        this.code = code;
        this.header = header;
    }

    public ApiException(HttpStatusCode code) {
        this(code, ResponseHeader.empty());
    }

    public HttpStatusCode getCode() {
        return code;
    }

    public ResponseHeader getHeader() {
        return header;
    }
}
