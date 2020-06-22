package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    private Map<String, String> header;

    public HttpHeaders() {
        header = new HashMap<>();
    }

    public HttpHeaders(Map<String, String> header) {
        this.header = header;
    }

    public void put(String key, String value) {
        header.put(key, value.trim());
    }

    public String get(String key) {
        return header.get(key);
    }

    public String makeResponseHeader() {
        Set<String> keys =  header.keySet();
        String headerString = "";
        for(String key: keys) {
            headerString += String.format("%s: %s", key, header.get(key) + "\r\n");
        }

        return headerString;
    }

}
