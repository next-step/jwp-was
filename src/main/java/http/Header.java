package http;

import utils.StringUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Header {

    private static final String HEADER_DELIMITER = ":";
    public static final String REQUEST_HEADER_IS_INVALID = "request header is invalid.";
    private final Map<String, String> headers;

    public Header(List<String> headers) {
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
        return new String[]{values[0], values[1].trim()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header = (Header) o;
        return Objects.equals(headers, header.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    public String get(String name) {
        return this.headers.get(name);
    }

    public int size() {
        return this.headers.size();
    }
}
