package webserver.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HttpHeaders {
    public static final String DELIMITER = ":";
    public static final int KEY_POINT = 0;
    public static final int VALUE_POINT = 1;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_CONTENT_TYPE = "text/html";

    private final Map<String, String> headers = new HashMap<>();

    public static HttpHeaders newInstance(String[] attributes, int start, int limit) {
        HttpHeaders httpHeaders = new HttpHeaders();

        IntStream.range(start, limit)
                .forEach(index -> httpHeaders.add(attributes[index]));

        return httpHeaders;
    }

    public static HttpHeaders defaultResponseHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);

        return httpHeaders;
    }

    public void add(String headerStr) {
        String[] headerInfo = headerStr.split(DELIMITER);

        if (headerInfo.length != 2) {
            throw new IllegalArgumentException("유효한 속성 문자열이 아닙니다. value:"+headerStr);
        }

        headers.put(headerInfo[KEY_POINT].trim(), headerInfo[VALUE_POINT].trim());
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getAttribute(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException();
        }
        return headers.get(key);
    }
}
