package webserver.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.jknack.handlebars.internal.lang3.StringUtils.EMPTY;

public class HttpHeaders {
    public static final String DELIMITER = ": ";
    public static final int KEY_POINT = 0;
    public static final int VALUE_POINT = 1;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_CONTENT_TYPE = "text/html";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";
    public static final String LOCATION = "Location";
    public static final String INVALID_HEADER_KEY_VALUE_MSG = "유효한 속성 문자열이 아닙니다. value:";

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

    public static HttpHeaders from(BufferedReader br) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();

        String line = br.readLine();
        while (line != null && !EMPTY.equals(line)) {
            httpHeaders.add(line);
            line = br.readLine();
        }

        return httpHeaders;
    }

    public void add(String headerStr) {
        if (Objects.isNull(headerStr)) {
            throw new IllegalArgumentException(INVALID_HEADER_KEY_VALUE_MSG + headerStr);
        }

        String[] headerInfo = headerStr.split(DELIMITER);

        if (headerInfo.length != 2) {
            throw new IllegalArgumentException(INVALID_HEADER_KEY_VALUE_MSG + headerStr);
        }

        headers.put(headerInfo[KEY_POINT].trim(), headerInfo[VALUE_POINT].trim());
    }

    public void add(String key, String value) {
        headers.put(key, value);
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

    public int getContentLength() {
        String contentLength = getAttributeOrDefault(CONTENT_LENGTH, "0");
        return Integer.parseInt(contentLength);
    }

    public Cookie getCookie() {
        if (headers.containsKey(COOKIE)) {
            return Cookie.from(getAttribute(COOKIE));
        }
        return new Cookie();
    }

    @Override
    public String toString() {
        String headerStr = headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue() + "\r\n")
                .collect(Collectors.joining());
        return headerStr + "\r\n";
    }
}
