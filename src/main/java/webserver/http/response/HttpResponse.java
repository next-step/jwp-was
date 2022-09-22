package webserver.http.response;

import exception.NotFoundHttpHeaderException;
import webserver.http.HttpBody;
import webserver.http.HttpHeader;
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

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
