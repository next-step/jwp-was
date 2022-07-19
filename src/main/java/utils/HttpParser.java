package utils;

import model.RequestLine;
import org.springframework.util.StringUtils;
import types.HttpMethod;
import types.Protocol;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final String PATH_SEPARATOR = "\\?";
    public static final String QUERY_PARAMETER_SEPARATOR = "&";
    public static final String QUERY_PARAMETER_KEY_VALUE_SEPARATOR = "=";
    public static final String PROTOCOL_VERSION_SEPARATOR = "/";

    public static RequestLine parseRequestLine(String requestLine) {
        String[] requestLineData = requestLine.split(REQUEST_LINE_SEPARATOR);

        HttpMethod httpMethod = HttpMethod.find(requestLineData[0]);
        String path = getPath(requestLineData);
        Map<String, String> queryParameters = getQueryParameters(requestLineData);
        Protocol protocol = getProtocol(requestLineData);
        String version = getProtocolVersion(requestLineData);

        return new RequestLine(httpMethod, path, queryParameters, protocol, version);
    }

    private static String getPath(String[] requestLineData) {
        return requestLineData[1].split(PATH_SEPARATOR)[0];
    }

    private static Map<String, String> getQueryParameters(String[] requestLineData) {
        String rawParameters = requestLineData[1].split(PATH_SEPARATOR)[1];
        String[] parameterKeyAndValues = rawParameters.split(QUERY_PARAMETER_SEPARATOR);
        Map<String, String> parameters = new HashMap<>();
        Arrays.stream(parameterKeyAndValues)
                .forEach(parameter -> {
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

        return Protocol.find(protocol.split(PROTOCOL_VERSION_SEPARATOR)[0]);
    }

    private static String getProtocolVersion(String[] requestLineData) {
        String protocol = requestLineData[2];
        if (!StringUtils.hasText(protocol)) {
            throw new IllegalArgumentException();
        }

        return protocol.split(PROTOCOL_VERSION_SEPARATOR)[1];
    }

}
