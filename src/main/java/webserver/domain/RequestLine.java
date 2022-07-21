package webserver.domain;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLine {
    public static final String DELIMITER = " ";
    public static final String QUERYSTRING_DELIMITER = "\\?";
    public static final int PATH_AND_QUERYSTRING_POINT = 1;
    public static final int PROTOCOL_AND_VERSION_POINT = 2;
    public static final int METHOD_POINT = 0;
    public static final int PATH_POINT = 0;
    public static final int QUERYSTRING_POINT = 1;
    public static final String QUERYSTRING_PARAM_DELIMITER = "&";
    public static final String QUERYSTRING_KEY_VALUE_DELIMITER = "=";
    public static final int QUERY_VALUE_POINT = 1;
    public static final String DEFAULT_QUERY_VALUE = "";

    private HttpMethod method;
    private Protocol protocol;
    private String path;
    private Map<String, String> parameterMap;

    public RequestLine(){}

    public RequestLine(HttpMethod method, String path, Map<String, String> parameterMap, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.parameterMap = parameterMap;
    }

    public static RequestLine from(String line) {
        String[] attributes = line.split(DELIMITER);
        String[] pathAndQueryString = attributes[PATH_AND_QUERYSTRING_POINT].split(QUERYSTRING_DELIMITER);
        Map<String, String> parameterMap = getParameterMap(pathAndQueryString);
        Protocol protocol = Protocol.newInstance(attributes[PROTOCOL_AND_VERSION_POINT]);

        return new RequestLine(HttpMethod.valueOf(attributes[METHOD_POINT]),
                pathAndQueryString[PATH_POINT],
                parameterMap,
                protocol);
    }

    private static Map<String, String> getParameterMap(String[] pathAndQueryString) {
        if (pathAndQueryString.length < 2) {
            return new HashMap<>();
        }

        return Arrays.stream(pathAndQueryString[QUERYSTRING_POINT].split(QUERYSTRING_PARAM_DELIMITER))
                .map(str -> str.split(QUERYSTRING_KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(RequestLine::queryKey, RequestLine::queryValue));
    }

    private static String queryKey(String[] pair) {
        return pair[0];
    }

    private static String queryValue(String[] pair) {
        if (pair.length < 2) {
            return DEFAULT_QUERY_VALUE;
        }
        return pair[QUERY_VALUE_POINT];
    }



    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Map<String, String> getParameterMap() {
        return Collections.unmodifiableMap(parameterMap);
    }
}
