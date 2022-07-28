package webserver.request;

import enums.HttpMethod;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Header header;

    public HttpRequest(List<String> request) {
        this.requestLine = new RequestLine(request.get(0));
        this.header = new Header(request.subList(1, request.size()));
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

    public Map<String, String> headers() {
        return header.getHeaders();
    }
}
