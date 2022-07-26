package webserver.http.request;

import java.util.Map;

public class Headers {
    private final Map<String, String> keyValues;

    public Headers(Map<String, String> keyValues) {
        this.keyValues = keyValues;
    }
}
