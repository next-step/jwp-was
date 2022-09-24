package webserver.http.response;

import exception.NotFoundHttpHeaderException;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;

public class HttpResponse {

    private final ResponseLine responseLine;
    private final HttpHeaders httpHeaders;

    public HttpResponse(ResponseLine responseLine, HttpHeaders httpHeaders) {
        this.responseLine = responseLine;
        this.httpHeaders = httpHeaders;
    }

    public String getRedirectFile() {
        return httpHeaders.getRedirectFile()
                .orElseThrow(() -> new NotFoundHttpHeaderException(HttpHeader.LOCATION));
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }
}
