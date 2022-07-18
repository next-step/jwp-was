package utils.http.parser;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import utils.http.HttpMethod;
import utils.http.HttpRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpRequestParser {

    private HttpRequestParser() {
    }

    private static final Pattern HTTP_REQUEST_PATTERN = Pattern.compile("(?<method>.+) (?<path>.+) (?<protocol>.+)/(?<version>.+)");
    private static final String REGEX_GROUP_METHOD = "method";
    private static final String REGEX_GROUP_PATH = "path";
    private static final String REGEX_GROUP_PROTOCOL = "protocol";
    private static final String REGEX_GROUP_VERSION = "version";

    public static final String DELIMITER_QUERY_STRING = "?";
    public static final String DELIMITER_PARAMS = "&";
    public static final String DELIMITER_KEY_VALUE = "=";
    public static final int IDX_KEY = 0;
    public static final int IDX_VALUE = 1;

    public static HttpRequest parse(String request) {
        Matcher matcher = HTTP_REQUEST_PATTERN.matcher(request);

        if (!matcher.find()) {
            throw new IllegalArgumentException("HTTP REQUEST 형식에 맞지 않습니다. request: " + request);
        }

        String method = matcher.group(REGEX_GROUP_METHOD);
        String pathWithQueryString = matcher.group(REGEX_GROUP_PATH);
        String path = getPath(pathWithQueryString);
        String protocol = matcher.group(REGEX_GROUP_PROTOCOL);
        String version = matcher.group(REGEX_GROUP_VERSION);

        String queryString = findQueryString(pathWithQueryString);
        Map<String, String> params = queryParsing(queryString);

        return new HttpRequest(HttpMethod.valueOf(method), path, protocol, version, params);
    }

    private static String getPath(String pathWithQueryString) {
        if (!pathWithQueryString.contains(DELIMITER_QUERY_STRING)) {
            return pathWithQueryString;
        }

        return pathWithQueryString.substring(0, pathWithQueryString.indexOf(DELIMITER_QUERY_STRING));
    }

    private static String findQueryString(String pathWithQueryString) {
        if (!pathWithQueryString.contains(DELIMITER_QUERY_STRING)) {
            return null;
        }

        return pathWithQueryString.substring(pathWithQueryString.indexOf(DELIMITER_QUERY_STRING) + 1);
    }

    private static Map<String, String> queryParsing(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return Collections.emptyMap();
        }

        String[] params = queryString.split(DELIMITER_PARAMS);

        return Arrays.stream(params)
                .map(param -> param.split(DELIMITER_KEY_VALUE))
                .collect(Collectors.toMap((keyAndValue -> keyAndValue[IDX_KEY]), (keyAndValue -> keyAndValue[IDX_VALUE])));
    }
}
