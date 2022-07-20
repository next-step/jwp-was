package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestLineParser {
    private static final Pattern REQUEST_LINE = Pattern.compile("[A-Z]* {1}\\S* {1}[A-Z]*\\/[0-9|.]+");
    private static final Pattern QUERY_STRING = Pattern.compile("^(\\/[a-zA-Z]*)*\\?([^=]+=+[^=]+)+[^=]+(=+[^=]+)?$");
    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final Integer METHOD_INDEX = 0;
    public static final Integer PATH_INDEX = 1;
    public static final Integer PROTOCOL_WITH_VERSION_INDEX = 2;

    static boolean isRequestLinePattern(String requestLine) {
        return REQUEST_LINE.matcher(requestLine).matches();
    }

    public static Map<String, String> parse(String requestLine) {
        validateCanParsing(requestLine);

        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);
        HashMap<String, String> parsedRequest = new HashMap<>();
        parsedRequest.put("method", split[METHOD_INDEX]);
        parsedRequest.put("path", split[PATH_INDEX]);

        Map<String, String> parsedProtocol = WebProtocolParser.parse(split[PROTOCOL_WITH_VERSION_INDEX]);
        parsedRequest.putAll(parsedProtocol);

        if (QUERY_STRING.matcher(parsedRequest.get("path")).matches()) {
            Map<String, String> parsedQueryString = QueryStringParser.parse(parsedRequest.get("path"));

            parsedRequest.putAll(parsedQueryString);
        }

        return parsedRequest;
    }

    private static void validateCanParsing(String requestLine) throws IllegalArgumentException {
        if (!isRequestLinePattern(requestLine)) {
            throw new IllegalArgumentException("Request Line이 올바른 형식을 가지고 있지 않습니다.");
        }
    }
}