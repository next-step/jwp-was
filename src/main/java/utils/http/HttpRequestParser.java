package utils.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    private static final Pattern REQUEST_LINE_PATTERN = Pattern.compile("(.+) (.+) (.+)/(.+)");

    private static final int GROUP_METHOD = 1;
    private static final int GROUP_PATH = 2;
    private static final int GROUP_PROTOCOL = 3;
    private static final int GROUP_VERSION = 4;

    public static HttpRequest parse(String requestLine) {
        Matcher matcher = REQUEST_LINE_PATTERN.matcher(requestLine);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Http Request 형식에 맞지 않습니다 : " + requestLine);
        }

        String method = matcher.group(GROUP_METHOD);
        String path = matcher.group(GROUP_PATH);
        String protocol = matcher.group(GROUP_PROTOCOL);
        String version = matcher.group(GROUP_VERSION);

        return new HttpRequest(method, path, protocol, version);
    }
}
