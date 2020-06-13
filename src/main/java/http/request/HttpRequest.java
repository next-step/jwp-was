package http.request;

import http.common.Cookies;
import webserver.session.HttpSession;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader header;
    private final Cookies cookies;
    private final String body;
    private HttpSession session;

    public HttpRequest(RequestLine requestLine, RequestHeader header, Cookies cookies, String body, HttpSession session) {
        this.requestLine = requestLine;
        this.header = header;
        this.cookies = cookies;
        this.body = body;
        this.session = session;
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
        return cookies.getValue(cookieName);
    }

    public HttpSession getSession(boolean create) {
        if (session == null && create) {
            this.session = HttpSession.create();
        }
        return session;
    }
}
