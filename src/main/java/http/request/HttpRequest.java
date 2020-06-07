package http.request;

import http.Cookie;
import http.Headers;
import http.HttpMethod;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Headers headers;
    private final String body;

    public HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
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
        String cookieStr = headers.getValue("Cookie");
        Cookie cookie = new Cookie(cookieStr);
        return cookie.getValue(cookieName);
    }
}
