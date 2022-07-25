package webserver.exception;

import webserver.response.HttpStatusCode;

import java.util.Collections;
import java.util.Map;

public final class ApiException extends RuntimeException {

    private final HttpStatusCode code;
    private final Map<String, String> header;

    public ApiException(HttpStatusCode code, Map<String, String> header) {
        super(code.toString());
        this.code = code;
        this.header = header;
    }

    public ApiException(HttpStatusCode code) {
        this(code, Collections.emptyMap());
    }

    public HttpStatusCode getCode() {
        return code;
    }

    public Map<String, String> getHeader() {
        return header;
    }
}
