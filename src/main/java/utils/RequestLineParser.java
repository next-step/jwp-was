package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestLineParser implements Parser {
    private final Pattern REQUEST_LINE = Pattern.compile("[A-Z]* {1}\\S* {1}[A-Z]*\\/[0-9|.]+");
    private final Pattern QUERY_STRING = Pattern.compile("^(\\/[a-zA-Z]*)*\\?([^=]+=+[^=]+)+[^=]+(=+[^=]+)?$");
    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final Integer METHOD_INDEX = 0;
    public static final Integer PATH_INDEX = 1;
    public static final Integer PROTOCOL_WITH_VERSION_INDEX = 2;

    boolean isRequestLinePattern(String requestLine) {
        return REQUEST_LINE.matcher(requestLine).matches();
    }

    public Map<String, String> parse(String requestLine) {
        validateCanParsing(requestLine);

        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);
        HashMap<String, String> parsedRequest = new HashMap<>();
        parsedRequest.put("method", split[METHOD_INDEX]);
        parsedRequest.put("path", split[PATH_INDEX]);

        WebProtocolParser webProtocolParser = new WebProtocolParser();
        Map<String, String> parsedProtocol = webProtocolParser.parse(split[PROTOCOL_WITH_VERSION_INDEX]);
        parsedRequest.putAll(parsedProtocol);

        if (QUERY_STRING.matcher(parsedRequest.get("path")).matches()) {
            QueryStringParser queryStringParser = new QueryStringParser();
            Map<String, String> parsedQueryString = queryStringParser.parse(parsedRequest.get("path"));

            parsedRequest.putAll(parsedQueryString);
        }

        return parsedRequest;
    }

    private void validateCanParsing(String requestLine) throws IllegalArgumentException {
        if (!isRequestLinePattern(requestLine)) {
            throw new IllegalArgumentException("Request Line이 올바른 형식을 가지고 있지 않습니다.");
        }
    }
}