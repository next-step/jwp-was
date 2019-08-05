package webserver.http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {

    private final Map<RequestHeaderFields, String> requestHeaders = new HashMap<>();

    public RequestHeader(final List<String> httpRequestHeaders) {
    }
}
