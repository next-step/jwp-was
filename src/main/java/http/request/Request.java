package http.request;

import http.response.Cookie;
import http.response.Cookies;
import webserver.session.HttpSession;
import webserver.session.Sessions;

public class Request {
    private static final String JSESSIONID = "JSESSIONID";

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
        String header = headers.getHeader("Set-Cookie");
        if(header == null){
            this.headers.addHeader("Set-Cookie", cookie.toString());
        }

        if(header != null){
            Cookies cookies = Cookies.parseCookies(header);
            cookies.addCookie(cookie);
            this.headers.replaceHeader("Set-Cookie", cookies.toString());
        }
    }

    public Cookies getCookies() {
        String cookieValue = this.getHeader("Set-Cookie");
        return Cookies.parseCookies(cookieValue);
    }
}
