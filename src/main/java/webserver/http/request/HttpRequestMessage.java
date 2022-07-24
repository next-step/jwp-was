package webserver.http.request;

import webserver.http.request.body.HttpRequestBody;
import webserver.http.request.header.HttpRequestHeaders;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.request.requestline.HttpQueryStrings;
import webserver.http.request.requestline.HttpRequestLine;

import java.util.List;

public class HttpRequestMessage {
    private HttpRequestLine httpRequestLine;
    private HttpRequestHeaders httpRequestHeaders;
    private HttpRequestBody httpRequestBody;

    public HttpRequestMessage(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public HttpRequestMessage(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestBody = httpRequestBody;
    }

    public HttpRequestMessage(HttpRequestLine httpRequestLine, List<String> httpRequestMessageLines) {

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

    public String getBodyValue(String bodyName) {
        return httpRequestBody.getBodyValue(bodyName);
    }

    public HttpQueryStrings getHttpQueryStrings() {
        assert httpRequestLine.getHttpPath() != null;

        return httpRequestLine.getHttpPath().getHttpQueryStrings();
    }
}
