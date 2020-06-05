package http;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;

    private HttpRequest(final RequestLine requestLine, final RequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }

    public static HttpRequest init(final RequestLine requestLine, RequestHeader requestHeader) {
        return new HttpRequest(requestLine, requestHeader);
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
        return requestHeader.getHeader(headerName);
    }
}
