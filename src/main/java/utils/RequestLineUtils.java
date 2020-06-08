package utils;

import org.apache.logging.log4j.util.Strings;

public class RequestLineUtils {
    private static final String REGEX_PROTOCOL_DELIMITER = "/";
    private static final String REGEX_PATH_DELIMITER = "\\?";
    private static final String REGEX_REQUESTLINE_DELIMETER = " ";
    private static final int LENGTH_TWO = 2;
    private static final int INDEX_FIRST = 0;
    private static final int INDEX_SECOND = 1;
    private static final int INDEX_THIRD = 2;

    public static String getUrl(String requestLine) {
        return splitPath(extractPath(requestLine))[INDEX_FIRST];
    }

    public static String getQueries(String requestLine) {
        String[] pathAndQuery = splitPath(extractPath(requestLine));

        if (pathAndQuery.length == LENGTH_TWO) {
            return pathAndQuery[INDEX_SECOND];
        }

        return Strings.EMPTY;
    }

    public static String getProtocol(String requestLine) {
        return splitProtocol(extractProtocol(requestLine))[0];
    }

    public static String getVersion(String requestLine) {
        return splitProtocol(extractProtocol(requestLine))[1];
    }

    public static boolean hasQueryStrings(String requestLine) {
        String[] strings = splitPath(extractPath(requestLine));
        return strings.length == LENGTH_TWO;
    }

    private static String[] splitProtocol(String protocol) {
        return protocol.split(REGEX_PROTOCOL_DELIMITER);
    }

    private static String extractProtocol(String requestLine) {
        return requestLine.split(REGEX_REQUESTLINE_DELIMETER)[INDEX_THIRD];
    }

    private static String[] splitPath(String path) {
        return path.split(REGEX_PATH_DELIMITER);
    }

    private static String extractPath(String requestLine) {
        return requestLine.split(REGEX_REQUESTLINE_DELIMETER)[1];
    }
}
