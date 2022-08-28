package webserver.http.domain;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String HEADER_DELIMITER = ": ";

    private final Map<String, String> headers = new HashMap<>();

    public void addHeader(String header) {
        if (hasHeader(header)) {
            String[] split = header.split(HEADER_DELIMITER);
            this.headers.put(split[0], split[1]);
        }
    }

    public String getValue(String name) {
        return headers.get(name).trim();
    }

    public boolean hasHeader(String headerLine) {
        return headerLine != null && !headerLine.isBlank();
    }
}