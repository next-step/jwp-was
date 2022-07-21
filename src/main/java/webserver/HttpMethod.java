package webserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpMethod {
    private static final String INVALID_HTTP_REQUEST = "잘못된 HTTP 요청 ";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int NOT_FOUND = -1;
    private static final int REQUEST_LINE_SIZE = 3;
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private String path;
    private String method;
    private Map<String, String> params = new HashMap<>();

    public HttpMethod(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public HttpMethod(String[] item) {
        if (item.length != REQUEST_LINE_SIZE) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST);
        }
        this.method = item[METHOD_INDEX];
        parseParameter(item[PATH_INDEX]);
    }

    private void parseParameter(String path) {
        if (method.equals("POST")) {
            this.path = path;
            return;
        }
        int paramIndex = path.indexOf(QUERY_STRING_DELIMITER);
        if (paramIndex == NOT_FOUND) {
            this.path = path;
            return;
        }
        this.path = path.substring(0, paramIndex);
        this.params = parseQueryString(path.substring(paramIndex + 1));
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, PARAM_DELIMITER);
    }

    private Map<String, String> parseValues(String values, String separator) {
        if (values.isEmpty()) {
            return new HashMap<>();
        }
        String[] tokens = values.split(separator);
        return Arrays.stream(tokens)
                .map(it -> Param.of(it, KEY_VALUE_DELIMITER))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Param::getKey, Param::getValue));
    }
}
