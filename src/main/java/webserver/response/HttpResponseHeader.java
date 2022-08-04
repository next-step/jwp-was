package webserver.response;

import static exception.ExceptionStrings.INVALID_HEADER_KEY;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class HttpResponseHeader {

    private Map<String, String> headers = Maps.newHashMap();

    private HttpResponseHeader() {
    }

    public static HttpResponseHeader createEmpty() {
        return new HttpResponseHeader();
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException(INVALID_HEADER_KEY);
        }

        return headers.get(key);
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(headers.keySet());
    }

}
