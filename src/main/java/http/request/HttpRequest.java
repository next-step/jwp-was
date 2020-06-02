package http.request;

import java.util.Map;

public class HttpRequest {
    private RequestLine requestLine;
    private Map<String, String> headers;
    private String body;

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest(RequestLine requestLine, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }
}
