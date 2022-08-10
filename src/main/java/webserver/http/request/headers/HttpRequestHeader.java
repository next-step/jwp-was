package webserver.http.request.headers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeader {

    private static final String DELIMITER = ":";

    private Map<String, String> requestHeaderValues = new HashMap<>();

    public HttpRequestHeader(final List<String> headerValues) {
        for (String headerValue : headerValues) {
            String[] split = headerValue.split(DELIMITER);
            requestHeaderValues.put(split[0], split[1].trim());
        }
    }

}
