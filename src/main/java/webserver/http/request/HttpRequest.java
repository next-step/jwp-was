package webserver.http.request;

public class HttpRequest implements Request {
    private final RequestLine requestLine;

    private final Header header;

    public HttpRequest(RequestLine requestLine, Header header) {
        this.requestLine = requestLine;
        this.header = header;
    }

    @Override
    public String getPath() {
        return requestLine.getUri().getPath();
    }

    @Override
    public Method getMethod() {
        return requestLine.getMethod();
    }

    @Override
    public QueryString getQueryString() {
        return requestLine.getUri().getQueryString();
    }
}
