package utils;

import model.RequestLine;
import model.WebProtocol;

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

    public RequestLine parse(String requestLine) {
        validateCanParsing(requestLine);

        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);
        String method = split[METHOD_INDEX];
        String path = split[PATH_INDEX];

        WebProtocolParser webProtocolParser = new WebProtocolParser();
        WebProtocol webProtocol = webProtocolParser.parse(split[PROTOCOL_WITH_VERSION_INDEX]);

        if (QUERY_STRING.matcher(path).matches()) {
            QueryStringParser queryStringParser = new QueryStringParser();
            String queryString = queryStringParser.parse(path);

            return new RequestLine(method, path, queryString, webProtocol);
        }

        return new RequestLine(method, path, webProtocol);
    }

    private void validateCanParsing(String requestLine) throws IllegalArgumentException {
        if (!isRequestLinePattern(requestLine)) {
            throw new IllegalArgumentException("Request Line이 올바른 형식을 가지고 있지 않습니다.");
        }
    }
}