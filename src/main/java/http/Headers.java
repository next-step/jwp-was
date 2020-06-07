package http;

import java.util.Map;

public class Headers {
    private final Map<String, Header> headers;

    public Headers(Map<String, Header> headers) {
        this.headers = headers;
    }

    public String getValue(String headerName) {
        Header header = headers.get(headerName);
        if (header == null) {
            return "";
        }
        return header.getValue();
    }
}
