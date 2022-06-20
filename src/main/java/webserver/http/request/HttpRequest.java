package webserver.http.request;

public class HttpRequest implements Request {
    private final RequestLine requestLine;

    private final Header header;

    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, Header header, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.header = header;
        this.requestBody = requestBody;
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

    @Override
    public RequestBody getRequestBody() {
        return requestBody;
    }

    public boolean isGet() {
        return getMethod() == Method.GET;
    }

    public boolean isPost() {
        return getMethod() == Method.POST;
    }
}
