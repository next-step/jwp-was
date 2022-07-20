package model;

import types.HttpMethod;
import types.Protocol;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final UrlPath urlPath;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod, UrlPath urlPath, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.urlPath = urlPath;
        this.protocol = protocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public UrlPath getUrlPath() {
        return urlPath;
    }

    public Protocol getProtocol() {
        return protocol;
    }

}
