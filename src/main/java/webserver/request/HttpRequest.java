package webserver.request;

import enums.HttpMethod;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Header header;
    private RequestBody body;

    public HttpRequest(RequestLine requestLine, Header header, RequestBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public HttpRequest(List<String> request) {
        this.requestLine = new RequestLine(request.get(0));
        this.header = new Header(request.subList(1, request.size()));
        this.body = null;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    public RequestBody getBody() {
        return this.body;
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

    public QueryString getQueryString() {
        return requestLine.getQueryString();
    }

    public Map<String, String> getHeaders() {
        return header.getHeaders();
    }
}
