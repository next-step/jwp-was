package webserver.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HttpHeaders {
    public static final String DELIMITER = ":";
    public static final int KEY_POINT = 0;
    public static final int VALUE_POINT = 1;

    private final Map<String, String> headers = new HashMap<>();

    public static HttpHeaders newInstance(String[] attributes, int start, int limit) {
        HttpHeaders httpHeaders = new HttpHeaders();

        IntStream.range(start, limit)
                .forEach(index -> httpHeaders.add(attributes[index]));

        return httpHeaders;
    }

    private void add(String headerStr) {
        String[] headerInfo = headerStr.split(DELIMITER);

        headers.put(headerInfo[KEY_POINT].trim(), headerInfo[VALUE_POINT].trim());
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
}
