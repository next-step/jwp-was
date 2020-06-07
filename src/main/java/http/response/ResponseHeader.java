package http.response;

import utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private static final String LOCATION = "Location";
    private static final String FORWARD = "forward";
    private final Map<String, String> headers = new HashMap<>();

    private ResponseHeader() {}

    public static ResponseHeader init() {
        return new ResponseHeader();
    }

    public void setLocation(final String location) {
        if (StringUtil.isEmpty(location)) {
            throw new IllegalArgumentException("Location can't be a null or empty string");
        }

        headers.clear();
        headers.put(LOCATION, location);
    }

    public String getLocation() {
        return headers.get(LOCATION);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public void setForward(final String forward) {
        if (StringUtil.isEmpty(forward)) {
            throw new IllegalArgumentException("Forward can't be a null or empty string");
        }

        headers.put(FORWARD, forward);
    }

    public String getForward() {
        return headers.remove(FORWARD);
    }
}
