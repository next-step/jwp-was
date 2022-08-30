package webserver.http.response;

import webserver.http.ContentType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static model.Constant.CONTENT_TYPE;
import static model.Constant.LOCATION;

public class ResponseHeader {
    public static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    public final static String EXTENSION_SEPARATOR = ".";

    private final Map<String, Object> headers;

    public ResponseHeader() {
        this.headers = new HashMap<>();
    }

    public ResponseHeader(Map<String, Object> headers) {
        this.headers = headers;
    }

    public ResponseHeader(String key, Object value) {
        this.headers = new HashMap<>();
        this.headers.put(key, value);
    }

    public void setHeader(String name, Object value) {
        this.headers.put(name, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public Object getHeader(String key) {
        return headers.get(key);
    }

    public String getHeader() {
        StringBuffer sb = new StringBuffer();

        if (getContentType() != null) {
            sb.append(getContentType()).append(System.lineSeparator());
        }

        for (Map.Entry<String, Object> entry : this.headers.entrySet()) {
            sb.append(entry.getKey()).append(HEADER_KEY_VALUE_SEPARATOR).append(entry.getValue()).append(System.lineSeparator());
        }

        return sb.toString();
    }

    private String getContentType() {
        String location = String.valueOf(getHeader(LOCATION));

        if (location == null) {
            return null;
        }

        return CONTENT_TYPE + HEADER_KEY_VALUE_SEPARATOR + getContentType(location).getValue();
    }

    private ContentType getContentType(String value) {
        if (value.contains(EXTENSION_SEPARATOR)) {
            return ContentType.getContentTypeFromExtension(value.substring(value.lastIndexOf(EXTENSION_SEPARATOR) + 1));
        }
        return ContentType.HTML;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHeader that = (ResponseHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "headers=" + headers +
                '}';
    }
}
