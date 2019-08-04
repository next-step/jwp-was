package webserver.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-08-04.
 */
public class Headers {
    private static final String HEADER_DELIMITER = ":";
    private static final int BEGIN_INDEX = 0;

    private Map<String, String> headers = new HashMap<>();

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void add(String rawHeader) {
        int delimiterIndex = rawHeader.indexOf(HEADER_DELIMITER);
        String name = rawHeader.substring(BEGIN_INDEX, delimiterIndex);
        String value = rawHeader.substring(delimiterIndex + 1);
        headers.put(name.trim(), value.trim());
    }
}
