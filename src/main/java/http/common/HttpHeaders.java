package http.common;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {
    public static final HttpHeaders EMPTY = new HttpHeaders(Collections.emptyMap());

    public static final String HEADER_KEY_VALUE_SPLITTER = ": ";
    public static final String HTTP_HEADER_LINE_JOINER = "\r\n";

    @Getter
    private Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        if (Objects.isNull(headers)) {
            throw new IllegalArgumentException();
        }

        this.headers = Collections.unmodifiableMap(headers);
    }

    public static HttpHeaders parse(BufferedReader br) throws IOException {
        Map<String, String> httpHeaders = Maps.newHashMap();
        String httpHeaderLine = br.readLine();

        while (!"".equals(httpHeaderLine)) {
            HttpHeader header = HttpHeader.of(httpHeaderLine);
            httpHeaders.put(header.getHeaderName(), header.getHeaderValue());
            httpHeaderLine = br.readLine();
        }

        return new HttpHeaders(httpHeaders);
    }

    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public boolean isNotEmpty() {
        return !headers.isEmpty();
    }

    @Override
    public String toString() {
        return headers.entrySet()
            .stream()
            .map(entry -> entry.getKey() + HEADER_KEY_VALUE_SPLITTER + entry.getValue())
            .collect(Collectors.joining(HTTP_HEADER_LINE_JOINER));
    }
}
