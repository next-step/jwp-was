package webserver.http;

import utils.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    private Map<String, String> headers = new HashMap<>();

    public void add(String key, String value) {
        if (StringUtils.isBlank(value)) return;
        headers.put(key, value);
    }

    public void add(String line) {
        int ind = line.indexOf(":");
        if (ind != -1) {
            String key = line.substring(0, ind);
            String value = line.substring(ind+1);
            add(key, value.trim());
        }
    }

    public String get(String key) {
        return headers.getOrDefault(key, "");
    }

    public Set<String> getHeaderKeys() {
        return new HashSet<>(headers.keySet());
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}
