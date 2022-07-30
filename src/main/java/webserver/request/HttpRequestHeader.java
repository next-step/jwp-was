package webserver.request;

import static exception.ExceptionStrings.CANNOT_FIND_HEADER_KEY;
import static webserver.domain.HttpHeader.CONTENT_LENGTH;

import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import webserver.domain.HttpHeader;

public class HttpRequestHeader {
    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_LINE_COUNT = 2;

    private Map<String, String> headers = Maps.newHashMap();

    private HttpRequestHeader() { }

    public HttpRequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (!line.isEmpty()) {
            addHeader(line);
            line = br.readLine();
        }
    }

    public static HttpRequestHeader createEmpty() {
        return new HttpRequestHeader();
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException(CANNOT_FIND_HEADER_KEY);
        }

        return headers.get(key);
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(headers.keySet());
    }

    private void addHeader(String headerLine) {
        if (headerLine == null) {
            return;
        }

        String[] keyVal = headerLine.split(HEADER_DELIMITER);
        if (keyVal.length == HEADER_LINE_COUNT) {
            this.headers.put(keyVal[0], keyVal[1].trim());
        }
    }

    public int contentLength() {
        if (headers.containsKey(CONTENT_LENGTH)) {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        }

        return 0;
    }

    public boolean hasContent() {
        return headers.containsKey(HttpHeader.CONTENT_LENGTH);
    }

}
