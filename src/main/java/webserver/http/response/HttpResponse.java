package webserver.http.response;

import webserver.http.HttpBody;
import webserver.http.HttpHeaders;

public class HttpResponse {

    private final ResponseLine responseLine;
    private final HttpHeaders httpHeaders;
    private final HttpBody httpBody;

    public HttpResponse(ResponseLine responseLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.responseLine = responseLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
