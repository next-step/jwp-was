package webserver.request;

import java.util.Map;
import webserver.enums.HttpMethod;
import webserver.enums.Protocol;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    public HttpRequest(String requestLine) {
        this.requestLine = RequestLine.of(requestLine);
        this.httpHeader = new HttpHeader();
        this.httpBody = new HttpBody();
    }

    public Map<String, String> bodyQueryString() {
        return httpBody.getBodyMap();
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

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }

    public Map<String, String> getQueryStringsMap() {
        return requestLine.getQueryStringsMap();
    }

    public void addHeader(String key, String value) {
        httpHeader.putHeader(key, value);
    }

    public void addHeader(String headerLine) {
        httpHeader.addHeader(headerLine);
    }

    public String getHeader(String key) {
        return httpHeader.getHeader(key);
    }

    public int contentLength() {
        return httpHeader.contentLength();
    }

    public void setBody(String body) {
        httpBody = new HttpBody(body);
    }

}
