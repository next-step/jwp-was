package http.request;

import http.Cookies;
import http.HeaderFieldName;
import http.HttpMethod;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Header header;
    private final String body;

    public HttpRequest(RequestLine requestLine, Header header, String body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getBody() {
        return body;
    }

    public String getCookie(String cookieName) {
        String cookieStr = header.getValue(HeaderFieldName.COOKIE);
        Cookies cookies = new Cookies(cookieStr);
        return cookies.getValue(cookieName);
    }
}
