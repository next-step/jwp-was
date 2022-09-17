package webserver.http.domain;

public class HttpRequest {
    private final RequestHeader requestHeader;
    private final RequestLine requestLine;
    private final RequestBody requestBody;

    public HttpRequest(RequestHeader requestHeader, RequestLine requestLine, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestLine = requestLine;
        this.requestBody = requestBody;
    }

    public RequestHeader requestHeader() {
        return requestHeader;
    }

    public RequestBody requestBody() {
        return requestBody;
    }

    public String path() {
        return this.requestLine.path();
    }

    public boolean startsWith(String prefix) {
        return this.requestLine.startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return this.requestLine.endsWith(suffix);
    }
}
