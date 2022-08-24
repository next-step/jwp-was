package model.request;

import model.Cookie;
import model.HttpHeaders;
import model.RequestParameters;
import utils.HttpMethod;
import webserver.RequestLine;

public class HttpRequest {
    private final HttpHeaders httpHeaders;
    private final RequestLine requestLine;
    private final RequestBody requestBody;
    private final Cookie cookie;

    public HttpRequest(HttpHeaders httpHeaders, RequestLine requestLine, RequestBody requestBody, Cookie cookie) {
        this.httpHeaders = httpHeaders;
        this.requestLine = requestLine;
        this.requestBody = requestBody;
        this.cookie = cookie;
    }

    public HttpRequest(HttpHeaders httpHeaders, RequestLine requestLine, RequestBody requestBody) {
        this(httpHeaders, requestLine, requestBody, Cookie.from(""));
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getHttpPath() {
        return requestLine.getHttpPath().getPath();
    }

    public RequestParameters getRequestParameters() {
        RequestParameters requestParameters = requestLine.getRequestParameters();
        if (HttpMethod.POST.equals(requestLine.getMethod())) {
            requestParameters = new RequestParameters(requestBody.getValue());
        }
        return requestParameters;
    }
}
