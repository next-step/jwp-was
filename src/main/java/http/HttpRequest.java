package http;

public class HttpRequest {
    private final RequestLine requestLine;

    private HttpRequest(final RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public static HttpRequest init(final RequestLine requestLine) {
        return new HttpRequest(requestLine);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
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
}
