package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    static final String HEADER_DELIMITER = ":";
    private final Map<String, String> headers = new HashMap<>();

    public void add(String header) {
        String[] tokens = header.split(HEADER_DELIMITER);
        headers.put(tokens[0], tokens[1].trim());
    }

    public String get(String name) {
        return headers.get(name);
    }

    public int getInt(String name) {
        String header = get(name);
        if (header == null) {
            return 0;
        }
        return Integer.parseInt(header);
    }
}
