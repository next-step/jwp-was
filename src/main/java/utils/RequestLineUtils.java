package utils;

public class RequestLineUtils {
    private static final String REGEX_PROTOCOL_DELIMITER = "/";
    private static final String REGEX_PATH_DELIMITER = "\\?";
    private static final String REGEX_REQUESTLINE_DELIMETER = " ";

    public static String getUrl(String requestLine) {
        return splitPath(extractPath(requestLine))[0];
    }

    public static String getQueries(String requestLine) {
        return splitPath(extractPath(requestLine))[1];
    }

    public static String getProtocol(String requestLine) {
        return splitProtocol(extractProtocol(requestLine))[0];
    }

    public static String getVersion(String requestLine) {
        return splitProtocol(extractProtocol(requestLine))[1];
    }

    public static boolean hasQueryStrings(String requestLine){
        String[] strings = splitPath(extractPath(requestLine));
        if(strings.length == 2){
            return true;
        }
        return false;
    }

    private static String[] splitProtocol(String protocol) {
        return protocol.split(REGEX_PROTOCOL_DELIMITER);
    }

    private static String extractProtocol(String requestLine) {
        return requestLine.split(REGEX_REQUESTLINE_DELIMETER)[2];
    }

    private static String[] splitPath(String path) {
        return path.split(REGEX_PATH_DELIMITER);
    }

    private static String extractPath(String requestLine) {
        return requestLine.split(REGEX_REQUESTLINE_DELIMETER)[1];
    }
}
