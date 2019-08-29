package model.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseHeader {
    private StatusLine statusLine;
    private Map<String, String> attributes;

    private HttpResponseHeader(StatusLine statusLine) {
        this.statusLine = statusLine;
        attributes = new LinkedHashMap<>();
    }

    private HttpResponseHeader(StatusLine statusLine, Map<String, String> attributes) {
        this.statusLine = statusLine;
        this.attributes = attributes;
    }

    public static HttpResponseHeader of(StatusLine statusLine) {
        return new HttpResponseHeader(statusLine);
    }

    public static HttpResponseHeader of(StatusLine statusLine, Map<String, String> attributes) {
        return new HttpResponseHeader(statusLine, attributes);
    }

    public HttpResponseHeader putAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public String print() {
        StringBuilder result = new StringBuilder();
        result.append(statusLine.print()).append("\r\n");
        for (Map.Entry<String, String> elem : attributes.entrySet()) {
            result.append(elem.getKey()).append(": ").append(elem.getValue()).append("\r\n");
        }

        return result.toString();
    }
}
