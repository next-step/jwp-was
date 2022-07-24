package utils;

import model.QueryParameter;
import model.RequestLine;
import model.UrlPath;
import org.springframework.util.StringUtils;
import types.HttpMethod;
import types.Protocol;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final String PATH_SEPARATOR = "\\?";
    public static final String QUERY_PARAMETER_SEPARATOR = "&";
    public static final String QUERY_PARAMETER_KEY_VALUE_SEPARATOR = "=";

    public static RequestLine parseRequestLine(String requestLine) {
        String[] requestLineData = requestLine.split(REQUEST_LINE_SEPARATOR);

        HttpMethod httpMethod = HttpMethod.find(requestLineData[0]);
        UrlPath path = getPath(requestLineData);
        Protocol protocol = getProtocol(requestLineData);

        return new RequestLine(httpMethod, path, protocol);
    }

    private static UrlPath getPath(String[] requestLineData) {
        String path = requestLineData[1].split(PATH_SEPARATOR)[0];
        Map<String, String> queryParameters = getQueryParameters(requestLineData);
        if (queryParameters.isEmpty()) {
            return new UrlPath(path);
        }

        return new UrlPath(path, new QueryParameter(queryParameters));
    }

    private static Map<String, String> getQueryParameters(String[] requestLineData) {
        String pathAndParameters = requestLineData[1];
        if (!pathAndParameters.contains(QUERY_PARAMETER_SEPARATOR)) {
            return Collections.emptyMap();
        }

        String rawParameters = pathAndParameters.split(PATH_SEPARATOR)[1];
        String[] parameterKeyAndValues = rawParameters.split(QUERY_PARAMETER_SEPARATOR);
        Map<String, String> parameters = new HashMap<>();
        Arrays.stream(parameterKeyAndValues).forEach(parameter -> {
            String[] keyAndValue = parameter.split(QUERY_PARAMETER_KEY_VALUE_SEPARATOR);
            String parameterKey = keyAndValue[0];
            String parameterValue = keyAndValue[1];
            parameters.put(parameterKey, parameterValue);
        });

        return parameters;
    }

    private static Protocol getProtocol(String[] requestLineData) {
        String protocol = requestLineData[2];
        if (!StringUtils.hasText(protocol)) {
            throw new IllegalArgumentException();
        }

        return Protocol.find(protocol);
    }

}
