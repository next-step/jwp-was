package utils.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile("(.+) (.+) (.+)/(.+)");

    private static final int GROUP_METHOD = 1;
    private static final int GROUP_PATH_AND_QUERY_STRING = 2;
    private static final int GROUP_PROTOCOL = 3;
    private static final int GROUP_VERSION = 4;

    private static final String BEGIN_QUERY_STRING = "?";
    private static final int NOT_FOUND_IDX = -1;

    public static HttpRequest parse(String requestLine) {
        Matcher matcher = REQUEST_LINE_PATTERN.matcher(requestLine);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Http Request 형식에 맞지 않습니다 : " + requestLine);
        }

        String method = matcher.group(GROUP_METHOD);
        String pathWithQueryString = matcher.group(GROUP_PATH_AND_QUERY_STRING);
        String protocol = matcher.group(GROUP_PROTOCOL);
        String version = matcher.group(GROUP_VERSION);

        int queryStringAt = pathWithQueryString.indexOf(BEGIN_QUERY_STRING);

        String path = extractPath(pathWithQueryString, queryStringAt);
        String queryString = extractQueryString(pathWithQueryString, queryStringAt);

        return new HttpRequest(HttpMethod.valueOf(method), path, protocol, version, HttpQueryStringParser.parse(queryString));
    }

    private static String extractPath(String pathWithQueryString, int queryStringAt) {
        if (queryStringAt == NOT_FOUND_IDX) {
            return pathWithQueryString;
        }
        return pathWithQueryString.substring(0, queryStringAt);
    }

    private static String extractQueryString(String pathWithQueryString, int queryStringAt) {
        if (queryStringAt == NOT_FOUND_IDX) {
            return null;
        }
        return pathWithQueryString.substring(queryStringAt + 1);
    }
}
