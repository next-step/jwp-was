package webserver.http;

import utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_LINE_SEPARATOR = ": ";

    private Map<String, String> headerMap;

    public HttpHeaders() {
        headerMap = new HashMap<>();
    }

    private HttpHeaders(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static HttpHeaders parse(List<String> headerLines) {
        Map<String, String> headerMap = MapUtils.keyValueMap(headerLines.stream(), HEADER_LINE_SEPARATOR);
        return new HttpHeaders(headerMap);
    }

    public String get(String key) {
        return this.headerMap.get(key);
    }

    public void set(String key, String value) {
        this.headerMap.put(key, value);
    }
}
