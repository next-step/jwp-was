package http.httpresponse;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ResponseHeader {
    private static final Map<String, Object> EMPTY_MAP = Collections.emptyMap();
    private final Map<String, Object> headers;

    public ResponseHeader(Map<String, Object> headers) {
        if (CollectionUtils.isEmpty(headers)){
           headers = EMPTY_MAP;
        }

        this.headers = Collections.unmodifiableMap(headers);
    }

    public Set<Map.Entry<String, Object>> getHeaderEntries() {
        return headers.entrySet();
    }

    public static ResponseHeader empty() {
        return new ResponseHeader(EMPTY_MAP);
    }

    public ResponseHeader add(String header, Object value) {
        HashMap<String, Object> newHeader = new HashMap<>(headers);
        newHeader.put(header, value);
        return new ResponseHeader(newHeader);
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
}
