package webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {

    private static final String SEPARATOR = " ";
    private static final String QUERY_PREFIX = "\\?";
    private static final String QUERY_DELIMITER = "&";
    private static final String QUERY_KEY_VALUE_DELIMITER = "=";

    private String method;
    private String path;
    private Map<String, String> params;

    private RequestLine(String method, String path, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.params = Collections.unmodifiableMap(params);
    }

    public static RequestLine parse(String rawRequestLine) {
        String[] requestLine = rawRequestLine.split(SEPARATOR);
        String[] requestUri = requestLine[1].split(QUERY_PREFIX);
        return new RequestLine(requestLine[0], requestUri[0], parseQueryString(requestUri));
    }

    private static Map<String, String> parseQueryString(String[] requestUri) {
        if (requestUri.length == 1) {
            return Collections.emptyMap();
        }

        Map<String, String> params = new HashMap<>();
        for (String param : requestUri[1].split(QUERY_DELIMITER)) {
            String[] entry = param.split(QUERY_KEY_VALUE_DELIMITER);
            params.put(entry[0], entry[1]);
        }
        return params;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
