package webserver;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@EqualsAndHashCode
public class RequestLine {

    private static final String BLANK = " ";
    private static final String PATH_SEPARATOR = "\\?";
    private static final String ENTRY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private Map<String, String> queryParameters;

    public RequestLine(HttpMethod method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        queryParameters = new HashMap<>();
    }

    public RequestLine(String requestLine) {
        String[] split = requestLine.split(BLANK);
        this.method = HttpMethod.valueOf(split[0]);
        this.path = split[1];

        String protocolNameAndVersion = split[2];
        this.protocol = new Protocol(protocolNameAndVersion);
        this.queryParameters = parseQueryString(path);
    }

    private Map<String, String> parseQueryString(String path) {
        String[] queryString = path.split(PATH_SEPARATOR);
        if (isNonExistent(queryString)) {
            return Collections.emptyMap();
        }
        String urlParameters = queryString[1];
        return Arrays.stream(urlParameters.split(ENTRY_SEPARATOR))
                .map(entry -> entry.split(KEY_VALUE_SEPARATOR))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
    }

    private boolean isNonExistent(String[] queryString) {
        return queryString.length <= 1;
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
