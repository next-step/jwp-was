package http.request;

import webserver.session.HttpSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Headers {
    private static final String JSESSIONID = "JSESSIONID";

    private Map<String, String> headers = new HashMap<>();

    public Headers(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }

    public int getSize() {
        return this.headers.size();
    }

    public Set<String> getKeySet() {
        return headers.keySet();
    }

    public Headers addHeader(String key, String value) {
        this.headers.put(key, value);
        return new Headers(Collections.unmodifiableMap(this.headers));
    }

    public void replaceHeader(String key, String newValue){
        this.headers.replace(key, newValue);
    }
}
