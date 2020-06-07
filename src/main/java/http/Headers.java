package http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Headers implements Iterable {
    private final Map<String, Header> headers;

    public Headers() {
        this.headers = new HashMap<>();
    }

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

    public void addHeader(Header header) {
        this.headers.put(header.getKey(), header);
    }

    @Override
    public Iterator iterator() {
        return headers.keySet().iterator();
    }
}
