package model;

import enums.HttpMethod;

public class RequestLine {
    private HttpMethod httpMethod;
    private String path;
    private String queryString;
    private WebProtocol webProtocol;

    public RequestLine(String httpMethod, String path, String queryString, WebProtocol webProtocol) {
        this.httpMethod = HttpMethod.getHttpMethod(httpMethod);
        this.path = path;
        this.queryString = queryString;
        this.webProtocol = webProtocol;
    }

    public RequestLine(String httpMethod, String path, WebProtocol webProtocol) {
        this.httpMethod = HttpMethod.getHttpMethod(httpMethod);
        this.path = path;
        this.queryString = null;
        this.webProtocol = webProtocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    public WebProtocol getWebProtocol() {
        return webProtocol;
    }
}