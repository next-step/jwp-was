package webserver.http;

public class HttpRequest {
    private final RequestLine requestLine;

    private final HttpHeaders headers;

    private RequestParams requestParams = new RequestParams();

    public HttpRequest(RequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestParams.addQueryString(requestLine.getQueryString());
        this.requestParams.addBody(body);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    public String getParameter(String name) {
        return requestParams.getParameter(name);
    }

    public HttpCookie getCookies() {
        return headers.getCookies();
    }

    public HttpSession getSession() {
        return headers.getSession();
    }
}
