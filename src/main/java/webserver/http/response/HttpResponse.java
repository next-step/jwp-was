package webserver.http.response;

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

    public static HttpResponse redirect(HttpHeaders httpHeaders) {
        return new HttpResponse(ResponseLine.redirect(), httpHeaders, ResponseBody.empty());
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
