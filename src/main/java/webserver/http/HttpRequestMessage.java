package webserver.http;

import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.request.requestline.HttpRequestLine;

public class HttpRequestMessage {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeaders httpRequestHeaders;

    public HttpRequestMessage(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public HttpMethod httpMethod() {
        return httpRequestLine.getHttpMethod();
    }

    public String httpPath() {
        return httpRequestLine.getHttpPath().getFullPath();
    }

    public String getHeader(String headerName) {
        return httpRequestHeaders.get(headerName);
    }
}
