package webserver.response;

import webserver.request.Headers;

import java.util.HashMap;
import java.util.Map;

/**
 * 현재 DTO느낌으로 사용하고 있으나 오브젝트로 역할을 가져가야한다.
 */
public class HttpResponse {

    private final byte[] body;
    private final String code; // TODO enum으로 변경
    private final Headers headers;

    public HttpResponse(byte[] body, String code, Headers headers) {
        this.body = body;
        this.code = code;
        this.headers = headers;
    }

    public static HttpResponse response202() {
        return new HttpResponse(new byte[]{}, "202", Headers.empty());
    }

    public static HttpResponse response302(String location) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(Headers.LOCATION, location);
        return new HttpResponse(new byte[]{}, "302", new Headers(headerMap));
    }

    public byte[] getBody() {
        return body;
    }

    public String getCode() {
        return code;
    }

    public String getCookieStr() {
        return headers.get("Cookie");
    }

    public Headers getHeaders() {
        return headers;
    }

    public void putCookie(String key, String value) {
        headers.putCookie(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setTextHtml() {
        headers.setTextHtml();
    }

    public void setTextCss() {
        headers.setTextCss();
    }

    public String makeResponseHeader() {
        headers.put(Headers.CONTENT_LENGTH, String.valueOf(body.length));
        return headers.response();
    }
}
