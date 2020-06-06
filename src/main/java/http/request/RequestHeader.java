package http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> header = new HashMap<>();

    public RequestHeader(Map<String, String> header) {
        this.header = header;
    }
}
