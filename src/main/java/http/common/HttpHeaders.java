package http.common;

import utils.StringUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final String HEADER_DELIMITER = ": ";
    public static final String REQUEST_HEADER_IS_INVALID = "request header is invalid.";
    private final Map<String, String> headers;

    public HttpHeaders(List<String> headers) {
        this.headers = headers.stream()
                .filter(header -> !StringUtils.isEmpty(header))
                .map(this::parse)
                .collect(HashMap::new,
                        (m, v) -> m.put(v[0], v[1]),
                        HashMap::putAll);
    }

    private String[] parse(String header) {
        String[] values = header.split(HEADER_DELIMITER, 2);
        if (values.length != 2 || StringUtils.isEmpty(values[0]) || StringUtils.isEmpty(values[1])) {
            throw new IllegalArgumentException(REQUEST_HEADER_IS_INVALID);
        }
        return new String[]{values[0], values[1]};
    }

    public String get(String name) {
        return this.headers.get(name);
    }

    public int size() {
        return this.headers.size();
    }

    public void set(String name, String value) {
        this.headers.put(name, value);
    }

    public String toJoinedString() {
        return this.headers.entrySet()
                .stream()
                .map(header -> header.getKey() + HEADER_DELIMITER + header.getValue() + "\r\n")
                .collect(Collectors.joining());
    }

    public boolean hasCookieValue(String value) {
        String cookie = this.headers.get(HttpHeader.COOKIE.getText());
        return cookie != null && cookie.contains(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeaders httpHeaders = (HttpHeaders) o;
        return Objects.equals(headers, httpHeaders.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
