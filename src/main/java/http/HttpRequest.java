package http;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpRequestHeaders httpRequestHeaders;

    private HttpRequest(final RequestLine requestLine, final HttpRequestHeaders httpRequestHeaders) {
        this.requestLine = requestLine;
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public static HttpRequest init(final RequestLine requestLine, HttpRequestHeaders httpRequestHeaders) {
        return new HttpRequest(requestLine, httpRequestHeaders);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getExtension() {
        return requestLine.getExtension();
    }

    public String getParameter(final String parameter) {
        return requestLine.getParameter(parameter);
    }

    public String getProtocol() {
        return requestLine.getProtocol();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public String getHeader(final String headerName) {
        return httpRequestHeaders.getHeader(headerName);
    }
}
