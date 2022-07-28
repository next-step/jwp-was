package model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HandlerInvokeResult<T> {

    private final ResponseEntity<T> result;

    public HandlerInvokeResult(ResponseEntity<T> result) {
        this.result = result;
    }

    public ResponseEntity<T> getBody() {
        return result;
    }

    public HttpStatus getHttpStatus() {
        return this.result.getStatusCode();
    }

    public HttpHeaders getResponseHeaders() {
        return this.result.getHeaders();
    }
}
