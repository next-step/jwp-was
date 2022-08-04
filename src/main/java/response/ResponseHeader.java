package response;

import constant.HttpHeader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ResponseHeader {

    private Map<String, String> headers = new HashMap<>();

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public void add(String header, String value) {
        headers.put(header, value);
    }

    public Set<Map.Entry<String, String>> entries() {
        return headers.entrySet();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public static ResponseHeader from(Map<String, String> headers) {
        return new ResponseHeader(headers);
    }

    public static ResponseHeader  empty() {
        return new ResponseHeader(new HashMap<>());
    }

}
