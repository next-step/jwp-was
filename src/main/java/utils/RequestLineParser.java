package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestLineParser {
    private static final Pattern REQUEST_LINE = Pattern.compile("[A-Z]* {1}\\S* {1}[A-Z]*\\/[0-9|.]+");
    public static final String REQUEST_LINE_SEPARATOR = " ";
    public static final Integer METHOD_INDEX = 0;
    public static final Integer PATH_INDEX = 1;
    public static final Integer PROTOCOL_WITH_VERSION_INDEX = 2;
    public static final Integer PROTOCOL_INDEX = 0;
    public static final Integer PROTOCOL_VERSION_INDEX = 1;
    public static final String VERSION_SEPARATOR = "/";

    static boolean isRequestLinePattern(String requestLine) {
        return REQUEST_LINE.matcher(requestLine).matches();
    }

    public static Map<String, String> parsing(String requestLine) {
        validateCanParsing(requestLine);

        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR);

        HashMap<String, String> parsedRequest = new HashMap<>();
        parsedRequest.put("method", split[METHOD_INDEX]);
        parsedRequest.put("path", split[PATH_INDEX]);
        parsedRequest.putAll(parsingProtocol(split[PROTOCOL_WITH_VERSION_INDEX]));

        return parsedRequest;
    }

    private static void validateCanParsing(String requestLine) throws IllegalArgumentException {
        if (!isRequestLinePattern(requestLine)) {
            throw new IllegalArgumentException("Request Line이 올바른 형식을 가지고 있지 않습니다.");
        }
    }

    private static Map<String, String> parsingProtocol(String protocol) {
        String[] split = protocol.split(VERSION_SEPARATOR);

        HashMap<String, String> parsedProtocol = new HashMap<>();
        parsedProtocol.put("protocol", split[PROTOCOL_INDEX]);
        parsedProtocol.put("protocolVersion", split[PROTOCOL_VERSION_INDEX]);

        return parsedProtocol;
    }
}
