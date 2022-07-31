package model;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<String, Object> headers;

    public ResponseHeader(Map<String, Object> headers) {
        this.headers = headers;
    }

    public static ResponseHeader textHtml(int length) {
        Map<String, Object> map = new HashMap<>();
        map.put(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8");
        map.put(HttpHeaders.CONTENT_LENGTH, length);
        return new ResponseHeader(map);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }
}
