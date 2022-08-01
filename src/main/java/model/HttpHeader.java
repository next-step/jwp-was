package model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHeader {
    public static final String HTTP_HEADER_SEPARATOR = ": ";

    private LinkedHashMap<String, String> keyToValue;

    public HttpHeader(LinkedHashMap<String, String> keyToValue) {
        this.keyToValue = keyToValue;
    }

    public String getValueByKey(String key) {
        return keyToValue.get(key);
    }

    public List<String> getHttpHeaders() {
        return keyToValue
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + HTTP_HEADER_SEPARATOR + entry.getValue())
            .collect(Collectors.toList());
    }
}
