package webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-08-04.
 */
public class ResponseHeaders {
    public static final String LOCATION = "Location";

    private Map<String, String> headers = new HashMap<>();

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addLocation(String value) {
        headers.put(LOCATION, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
