package webserver.request;

import static exception.ExceptionStrings.CANNOT_FIND_HEADER_KEY;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class HttpHeader {

    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_LINE_COUNT = 2;

    private Map<String, String> headers = Maps.newHashMap();

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException(CANNOT_FIND_HEADER_KEY);
        }

        return headers.get(key);
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(headers.keySet());
    }

    public void addHeader(String headerLine) {
        if (headerLine == null) {
            return;
        }

        String[] keyVal = headerLine.split(HEADER_DELIMITER);
        if (keyVal.length == HEADER_LINE_COUNT) {
            this.headers.put(keyVal[0], keyVal[1].trim());
        }
    }

    public int contentLength() {
        if (headers.containsKey("Content-Length")) {
            return Integer.parseInt(headers.get("Content-Length"));
        }

        return 0;
    }
}
