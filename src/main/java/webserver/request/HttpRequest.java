package webserver.request;

import enums.HttpMethod;
import java.util.List;
import java.util.Map;
import webserver.Cookie;
import webserver.Cookies;
import webserver.HttpHeader;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpHeader headers;
    private final Cookies cookies;
    private RequestBody body;

    public HttpRequest(RequestLine requestLine, HttpHeader headers, Cookies cookies, RequestBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public HttpRequest(List<String> request) {
        this.requestLine = new RequestLine(request.get(0));
        this.headers = new HttpHeader(request.subList(1, request.size()));
        this.cookies = new Cookies(headers);
        this.body = null;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    public RequestBody getBody() {
        return this.body;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getProtocol() {
        return requestLine.getProtocol();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public QueryString getQueryString() {
        return requestLine.getQueryString();
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public Cookies getCookies() {
        return cookies;
    }

    public Cookie getCookie(String key) {
        return cookies.getCookie(key);
    }
}
