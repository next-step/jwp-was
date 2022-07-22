package webserver.request;

import java.util.Map;
import webserver.enums.HttpMethod;
import webserver.enums.RequestProtocol;

public class HttpRequest {

    private RequestLine requestLine;

    public HttpRequest(String requestLine) {
        this.requestLine = RequestLine.of(requestLine);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public RequestProtocol getProtocol() {
        return requestLine.getProtocol();
    }

    public Map<String, String> getQueryStringsMap() {
        return requestLine.getQueryStringsMap();
    }
}