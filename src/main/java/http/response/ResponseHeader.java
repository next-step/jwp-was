package http.response;

import utils.StringUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private static final String HEADER_FORMAT = "%s: %s \r\n";
    private static final String LOCATION = "Location";
    private final Map<String, String> headers = new HashMap<>();

    public ResponseHeader() {}

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

    public void writeHeaders(final DataOutputStream dataOutputStream) throws IOException {
        for (String key : headers.keySet()) {
            dataOutputStream.writeBytes(String.format(HEADER_FORMAT, key, headers.get(key)));
        }
    }
}
