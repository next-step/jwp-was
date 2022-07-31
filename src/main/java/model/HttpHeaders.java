package model;

import types.MediaType;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setLocation(String url) {
        this.headers.put("Location", url);
    }

    public void setContentType(MediaType mediaType) {
        this.headers.put("Content-Type", mediaType.getValue());
    }

    public void setCookie(String value) {
        this.headers.put("Set-Cookie", value);
    }
}
