package webserver.http.response;

import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.MediaType;

public class HttpResponse {

    private final ResponseLine responseLine;
    private final HttpHeaders httpHeaders;
    private final ResponseBody responseBody;

    public HttpResponse(ResponseLine responseLine, HttpHeaders httpHeaders, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.httpHeaders = httpHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse forward(MediaType mediaType, String resourcePath, String fileName) {
        ResponseLine responseLine = ResponseLine.ok();

        ResponseBody responseBody = ResponseBody.from(resourcePath, fileName);

        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_TYPE, mediaType.getChemical());
        httpHeaders.addResponseHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(responseBody.getContentsLength()));

        return new HttpResponse(responseLine, httpHeaders, responseBody);
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
