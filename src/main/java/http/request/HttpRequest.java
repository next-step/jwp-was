package http.request;

import http.HttpMethod;
import http.RequestLine;

import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;

    private final HttpRequestHeader requestHeaders;

    private final Map<String, String> parameters;

    public HttpRequest(RequestLine requestLine, HttpRequestHeader requestHeader, Map<String, String> parameters) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeader;
        this.parameters = parameters;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getProtocol() {
        return requestLine.getProtocol();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public String getHeader(String key) {
        return requestHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        if (requestLine.isGet()) {
            return requestLine.getParameter(key);
        }
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        if (requestLine.isGet()) {
            return requestLine.getParameters();
        }
        return parameters;
    }
}
