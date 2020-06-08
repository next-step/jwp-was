package http.request;

import http.common.Cookies;
import http.common.HeaderFieldName;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader header;
    private final String body;

    public HttpRequest(RequestLine requestLine, RequestHeader header, String body) {
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
