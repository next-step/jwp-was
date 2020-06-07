package http.request;

public class Request {
    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody requestBody;

    public Request(RequestLine requestLine, Headers headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getHeader(String key) {
        return getHeaders().getHeader(key);
    }
}
