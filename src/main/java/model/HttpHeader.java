package model;

import java.util.Map;

public class HttpHeader {
    private Map<String, String> keyToValue;

    public HttpHeader(Map<String, String> keyToValue) {
        this.keyToValue = keyToValue;
    }

    public String getValueByKey(String key) {
        return keyToValue.get(key);
    }
}
