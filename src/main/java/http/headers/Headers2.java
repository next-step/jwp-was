package http.headers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers2 {
    private Map<String, String> headers = new HashMap<>();

    public Headers2(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public String getParameter(String key) {
        return this.headers.get(key);
    }

    public int getSize(){
        return this.headers.size();
    }
}
