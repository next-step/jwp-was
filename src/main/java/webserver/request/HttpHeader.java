package webserver.request;

import static exception.ExceptionStrings.CANNOT_FIND_HEADER_KEY;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class HttpHeader {

    private Map<String, String> headers = Maps.newHashMap();

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException(CANNOT_FIND_HEADER_KEY);
        }

        return headers.get(key);
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(headers.keySet());
    }

}
