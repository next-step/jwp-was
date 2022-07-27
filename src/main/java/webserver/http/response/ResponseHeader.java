package webserver.http.response;

import utils.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ResponseHeader {

    private final Map<String, String> headers;

    private ResponseHeader(Map<String, String> headers) {
        Assert.notNull(headers, "'headers' must not be null");
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static ResponseHeader from(Map<String, String> headers) {
        return new ResponseHeader(Objects.requireNonNullElse(headers, Collections.emptyMap()));
    }

    public static ResponseHeader empty() {
        return from(Collections.emptyMap());
    }

    public Set<Map.Entry<String, String>> entries() {
        return headers.entrySet();
    }

    public ResponseHeader add(String header, String value) {
        HashMap<String, String> newHeader = new HashMap<>(headers);
        newHeader.put(header, value);
        return from(newHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseHeader that = (ResponseHeader) o;
        return Objects.equals(headers, that.headers);
    }
}
