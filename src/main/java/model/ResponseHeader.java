package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<String, Object> headers;

    private ResponseHeader(Map<String, Object> headers) {
        this.headers = headers;
    }

    public static ResponseHeader text(int length, String path) {
        Map<String, Object> map = new HashMap<>();
        map.put(HttpHeaders.CONTENT_TYPE, String.format("text/%s;charset=utf-8", fileExtension(path)));
        map.put(HttpHeaders.CONTENT_LENGTH, length);
        return new ResponseHeader(map);
    }

    public static ResponseHeader empty() {
        return new ResponseHeader(Collections.emptyMap());
    }

    public static ResponseHeader of(Map<String,Object> map) {
        return new ResponseHeader(map);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    private static String fileExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
