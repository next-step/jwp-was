package http;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(final RequestLine requestLine,
                        final RequestHeader requestHeader,
                        final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest init(final RequestLine requestLine,
                                   final RequestHeader requestHeader,
                                   final RequestBody requestBody) {
        return new HttpRequest(requestLine, requestHeader, requestBody);
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

    public String getBodyParameter(final String parameter) {
        return requestBody.getBodyParameter(parameter);
    }

    public String getCookie(final String cookieName) {
        return requestHeader.getCookie(cookieName);
    }
}
