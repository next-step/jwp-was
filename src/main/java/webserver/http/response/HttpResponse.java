package webserver.http.response;

import exception.NotFoundHttpHeaderException;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;

public class HttpResponse {

    private final ResponseLine responseLine;
    private final HttpHeaders httpHeaders;
    private final ResponseBody responseBody;

    public HttpResponse(ResponseLine responseLine, HttpHeaders httpHeaders, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.httpHeaders = httpHeaders;
        this.responseBody = responseBody;
    }

    public String getRedirectFile() {
        return httpHeaders.getRedirectFile()
                .orElseThrow(() -> new NotFoundHttpHeaderException(HttpHeader.LOCATION));
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public ResponseBody getBody() {
        return responseBody;
    }
}
