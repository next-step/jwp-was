package model.request;

import enums.HttpMethod;
import model.WebProtocol;

import java.util.Map;

import java.util.Map;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> queryString;
    private final WebProtocol webProtocol;

    public RequestLine(String httpMethod, String path, Map<String, String> queryString, WebProtocol webProtocol) {
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

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public WebProtocol getWebProtocol() {
        return webProtocol;
    }

    public Boolean hasQueryString() {
        return queryString != null;
    }
}