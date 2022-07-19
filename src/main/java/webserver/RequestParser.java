package webserver;

public class RequestParser {

    private static final int METHOD_PLACE_IDX = 0;
    private static final int PATH_PLACE_IDX = 1;
    private static final int PROTOCOL_INFO_PLACE_IDX = 2;

    private static final int PROTOCOL_PLACE_IDX = 0;
    private static final int PROTOCOL_VERSION_PLACE_IDX = 1;

    private static final String BLANK = " ";
    private static final String SLASH = "/";

    public static String parseMethod(String requestLine) {
        String[] lines = requestLine.split(BLANK);

        return lines[METHOD_PLACE_IDX];
    }

    public static String parsePath(String requestLine) {
        String[] lines = requestLine.split(BLANK);

        return lines[PATH_PLACE_IDX];
    }

    public static String parseProtocol(String requestLine) {
        String[] lines = requestLine.split(BLANK);

        String[] protocolInfo = lines[PROTOCOL_INFO_PLACE_IDX].split(SLASH);

        return protocolInfo[PROTOCOL_PLACE_IDX];
    }

    public static String parseProtocolVersion(String requestLine) {
        String[] lines = requestLine.split(BLANK);

        String[] protocolInfo = lines[PROTOCOL_INFO_PLACE_IDX].split(SLASH);

        return protocolInfo[PROTOCOL_VERSION_PLACE_IDX];
    }

    public static String parseQueryString(String requestLine) {
        String path = parsePath(requestLine);

        String[] paths = path.split("\\?");

        return paths[1];
    }
}
