package webserver.http.header;

import java.util.Map;

public class Headers {
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
        return keyValues.containsKey("Content-Length");
    }

    private int getContentLengthValue() {
        return Integer.parseInt(keyValues.get("Content-Length"));
    }

    public boolean hasContentType(String contentType) {
        String value = keyValues.get("Content-Type");
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

    @Override
    public String toString() {
        return "Headers{" +
                "keyValues=" + keyValues +
                '}';
    }
}
