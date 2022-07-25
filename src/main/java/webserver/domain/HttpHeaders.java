package webserver.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HttpHeaders {
    public static final String DELIMITER = ": ";
    public static final int KEY_POINT = 0;
    public static final int VALUE_POINT = 1;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_CONTENT_TYPE = "text/html";
    public static final String TEXT_HTML = "text/html";
    public static final String HEADER_ATTR_DELIMITER = ",";
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_ALL = "*/*";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

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
            throw new IllegalArgumentException("유효한 속성 문자열이 아닙니다. value:" + headerStr);
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

    public String getAttributeOrDefault(String key, String defaultValue) {
        return headers.getOrDefault(key, defaultValue);
    }

    public String getContentType() {
        return headers.getOrDefault(CONTENT_TYPE, TEXT_HTML);
    }

    public void setContentTypeFromAccept(String acceptStr) {
        String accept = acceptStr.split(HEADER_ATTR_DELIMITER)[0];

        if (!ACCEPT_ALL.equals(accept)) {
            add(CONTENT_TYPE, accept);
        }
    }

    public String getAccept() {
        return getAttribute(ACCEPT);
    }

    public void setContentLength(int contentLength) {
        if (contentLength > 0) {
            add(CONTENT_LENGTH, String.valueOf(contentLength));
        }
    }

    @Override
    public String toString() {
        return headers.entrySet().stream()
                .map(entry -> entry.getKey()+ ": "+ entry.getValue()+"\r\n")
                .collect(Collectors.joining());
    }

    public int getContentLength() {
        String contentLength = getAttributeOrDefault(CONTENT_LENGTH, "0");
        return Integer.parseInt(contentLength);
    }

    public Cookie getCookie() {
        return Cookie.from(getAttribute(COOKIE));
    }
}
