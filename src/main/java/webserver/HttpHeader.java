package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private static final String HEADER_DELIMITER = ": ";
    private static final int NAME_IDX = 0;
    private static final int VALUE_IDX = 1;

    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String headerLine) {
        if (hasHeader(headerLine)) {
            String[] split = headerLine.split(HEADER_DELIMITER);
            this.headers.put(split[NAME_IDX], split[VALUE_IDX]);
        }
    }

    public String getValue(String name) {
        return headers.get(name).trim();
    }

    public boolean hasHeader(String headerLine) {
        if (headerLine != null && !headerLine.isBlank()) {
            return true;
        }
        return false;
    }
    public HttpHeader() {
        this.headers = headers;
    }
}
