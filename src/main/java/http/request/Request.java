package http.request;

import http.response.Cookie;
import http.response.Cookies;
import webserver.session.HttpSession;
import webserver.session.Sessions;

public class Request {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String SET_COOKIE = "Set-Cookie";

    private final RequestLine requestLine;
    private final Headers headers;
    private final RequestBody requestBody;

    public Request(RequestLine requestLine, Headers headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public String getHeader(String key) {
        return this.headers.getHeader(key);
    }

    public Headers getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpSession getSession() {
        String sessionId = this.headers.getHeader(JSESSIONID);

        if (sessionId == null) {
            HttpSession session = new HttpSession();
            this.headers.addHeader(JSESSIONID, session.getId());
            return session;
        }

        return Sessions.findById(sessionId);
    }

    public void addCookie(Cookie cookie) {
        String cookieValue = headers.getHeader(SET_COOKIE);
        if (cookieValue == null) {
            this.headers.addHeader(SET_COOKIE, cookie.toString());
        }

        if (cookieValue != null) {
            Cookies cookies = Cookies.parseCookies(cookieValue);
            cookies.addCookie(cookie);
            this.headers.replaceHeader(SET_COOKIE, cookies.toString());
        }
    }

    public Cookies getCookies() {
        String cookieValue = this.getHeader(SET_COOKIE);
        return Cookies.parseCookies(cookieValue);
    }
}
