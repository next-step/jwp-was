package http.request;

import http.Cookies;
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
        Cookies cookies = new Cookies(cookieStr);
        return cookies.getValue(cookieName);
    }
}
