package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import javafx.util.Pair;
import utils.StringParseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        Map<String, String> headerMap = parseHeaderLines(bufferedReader).stream()
                .filter(StringUtils::isNotBlank)
                .map(headerLine -> StringParseUtils.makeKeyValuePair(headerLine, HEADER_LINE_SEPARATOR))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

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
