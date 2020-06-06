package http.requests;

import http.requests.parameters.Cookie;
import http.requests.parameters.FormData;
import http.types.HttpMethod;
import utils.HttpRequestParser;

import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeader requestHeaders;
    private final String body;
    private final FormData formData;
    private final Cookie cookie;

    public HttpRequest(String rawRequestLine, List<String> rawRequestHeaders, String body) {
        this.requestLine = new RequestLine(rawRequestLine);
        this.requestHeaders = HttpRequestParser.parseHeaders(rawRequestHeaders);
        this.body = body;
        this.formData = HttpRequestParser.parseFormData(body);
        final String cookieData = requestHeaders.getHeader("Cookie");
        this.cookie = HttpRequestParser.parseCookie(cookieData);
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", formData=" + formData +
                '}';
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getAttributeFromForm(String key) {
        return formData.get(key);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body;
    }

    public Cookie getCookie() {
        return cookie;
    }

}
