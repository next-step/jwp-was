package webserver.response;

import exception.Assert;
import http.request.Protocol;
import webserver.HttpHeader;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    public final static HttpResponse NOT_FOUND = of(
            StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.NOT_FOUND),
            HttpHeader.empty()
    );

    private StatusLine statusLine;
    private HttpHeader responseHeader;
    private ResponseBody responseBody;

    public HttpResponse(StatusLine statusLine, HttpHeader responseHeader, ResponseBody responseBody) {
        Assert.notNull(statusLine, "StatusLine이 null이어선 안됩니다.");
        Assert.notNull(responseHeader, "HttpHeader가 null이어선 안됩니다.");
        Assert.notNull(responseBody, "HttpBody가 null이어선 안됩니다.");
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(StatusLine statusLine, HttpHeader responseHeader) {
        return new HttpResponse(statusLine, responseHeader, new ResponseBody(new byte[0]));
    }

    public static HttpResponse of(StatusLine statusLine, HttpHeader responseHeader, byte[] body) {
        return new HttpResponse(statusLine, responseHeader, new ResponseBody(body));
    }

    public static HttpResponse of(StatusLine statusLine, HttpHeader responseHeader, String body) {
        return new HttpResponse(statusLine, responseHeader, new ResponseBody(body.getBytes(StandardCharsets.UTF_8)));
    }

    public HttpStatusCode getHttpStatusCode() {
        return statusLine.getHttpStatusCode();
    }

    public byte[] body() {
        return responseBody.getBody();
    }

    public int getContentLength() {
        return responseBody.getContentLength();
    }

    public String getResponseHeader(String key) {
        return responseHeader.get(key);
    }

    public Set<Map.Entry<String, String>> headerEntries() {
        return responseHeader.entries();
    }

    public Set<Map.Entry<String, String>> cookieEntries() {
        return responseHeader.getCookies();
    }
}
