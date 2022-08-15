package model;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaderBuilder {

    private static Map<String, String> headers = new HashMap<>();
    
    public HttpHeaderBuilder addLocation(String location) {
        headers.put("Location", location);
        return this;
    }

    public HttpHeaderBuilder addContentLength(int length) {
        headers.put("Content-Length", String.valueOf(length));
        return this;
    }

    public HttpHeaderBuilder addContentType(String contentType) {
        headers.put("Content-Type", contentType + ";charset=utf-8");
        return this;
    }
    public HttpHeaderBuilder addCookie(String name, String value) {
        headers.put("Set-Cookie", name + "=" + value);
        return this;
    }

    public HttpHeader build() {
        return new HttpHeader(headers);
    }
}
