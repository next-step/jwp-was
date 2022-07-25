package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeaders {

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeaders() {
    }

    public RequestHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void add(final String header) {
        if (header == null || header.isBlank()) {
            return;
        }

        final String[] keyAndValue = header.split(": ");
        if (keyAndValue.length == 1) {
            return;
        }
        headers.put(keyAndValue[0].trim(), keyAndValue[1].trim());
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getConnection() {
        return headers.get("Connection");
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }

    public String getAccept() {
        return headers.get("Accept");
    }

    public String get(final String key) {
        return headers.get(key);
    }

    public boolean hasRequestBody() {
        return headers.containsKey("Content-Length") && getContentLength() > 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestHeaders that = (RequestHeaders) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
