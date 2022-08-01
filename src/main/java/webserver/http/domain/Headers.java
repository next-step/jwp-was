package webserver.http.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class Headers {
    private static final String KEY_VALUE_DELIMITER = ": ";

    public static final String LOCATION = "Location";
    public static final String ACCEPT = "Accept";
    public static final String HOST = "Host";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

    private final Map<String, String> keyValues;

    public Headers(Map<String, String> keyValues) {
        this.keyValues = keyValues;
    }

    public boolean containsContentLength() {
        if (containsContentLengthKey()) {
            return getContentLengthValue() > 0;
        }
        return false;
    }

    public int getContentLength() {
        if (containsContentLength()) {
            return getContentLengthValue();
        }
        return 0;
    }

    private boolean containsContentLengthKey() {
        return keyValues.containsKey(CONTENT_LENGTH);
    }

    private int getContentLengthValue() {
        return Integer.parseInt(keyValues.get(CONTENT_LENGTH));
    }

    public boolean hasContentType(String contentType) {
        String value = keyValues.get(CONTENT_TYPE);
        return contentType.equals(value);
    }

    public String getValue(String name) {
        return keyValues.get(name);
    }

    public boolean contains(String name) {
        return keyValues.containsKey(name);
    }

    public void add(String name, String value) {
        keyValues.put(name, value);
    }

    public Set<String> getAllKeys() {
        return keyValues.keySet();
    }

    public static Headers from(List<String> messages) {
        return messages.stream()
                .map(message -> KeyValuePair.from(message, KEY_VALUE_DELIMITER))
                .collect(collectingAndThen(
                        toMap(
                                KeyValuePair::getKey,
                                KeyValuePair::getValue,
                                (oldValue, newValue) -> newValue,
                                LinkedHashMap::new
                        ),
                        Headers::new
                ));
    }

    @Override
    public String toString() {
        return "Headers{" +
                "keyValues=" + keyValues +
                '}';
    }
}
