package http.response;

import http.request.Headers;

public class Response {
    private static final String SET_COOKIE = "Set-Cookie";

    private HttpStatus status;
    private ContentType contentType;
    private ResponseBody body;
    private Headers headers;

    public Response(HttpStatus status, ContentType contentType, ResponseBody body) {
        new Response(status, contentType, null, body);
    }

    public Response(HttpStatus status, ContentType contentType, Headers headers, ResponseBody body) {
        this.status = status;
        this.contentType = contentType;
        this.headers = headers;
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body.getBody();
    }

    public String getHeaderByKey(String key) {
        return headers.getHeader(key);
    }

    public Headers getHeaders() {
        return headers;
    }

    public void addCookie(Cookie cookie) {
        String cookieValue = this.getHeaderByKey(SET_COOKIE);
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
        String headerByKey = this.getHeaderByKey(SET_COOKIE);
        return Cookies.parseCookies(headerByKey);
    }
}
