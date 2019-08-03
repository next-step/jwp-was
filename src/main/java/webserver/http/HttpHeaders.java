package webserver.http;

import utils.MapUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class HttpHeaders {
    private static final String HEADER_LINE_SEPARATOR = ": ";

    private Map<String, String> headerMap;

    public HttpHeaders() {
        headerMap = new HashMap<>();
    }

    private HttpHeaders(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static HttpHeaders parse(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headerMap = MapUtils.keyValueMap(parseHeaderLines(bufferedReader).stream(), HEADER_LINE_SEPARATOR);
        return new HttpHeaders(headerMap);
    }

    public String get(String key) {
        return this.headerMap.get(key);
    }

    public void set(String key, String value) {
        this.headerMap.put(key, value);
    }

    private static List<String> parseHeaderLines(BufferedReader bufferedReader) throws IOException {
        String line;
        List<String> headerLines = new ArrayList<>();
        while (!"".equals(line = bufferedReader.readLine())) {
            if (Objects.isNull(line)) break;
            headerLines.add(line);
        }
        return headerLines;
    }
}
