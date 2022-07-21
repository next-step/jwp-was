package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLineParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestLineParser.class);
    private static final String INVALID_HTTP_REQUEST = "잘못된 HTTP 요청 ";
    private static final String DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    public static final int PROTOCOL_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int VERSION_INDEX = 1;
    public static final int PROTOCOL_INFORMATION_SIZE = 2;
    private static final int MINIMUM_REQUEST_LINE_TOKEN = 3;
    public static final String PROTOCOL_DELIMITER = "/";
    private String[] items;


    public RequestLineParser(String line) {
        if (line == null) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST + line);
        }
        this.items = line.split(DELIMITER);
        if (items.length < MINIMUM_REQUEST_LINE_TOKEN) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST + line);
        }
    }

    public String getMethod() {
        return items[METHOD_INDEX];
    }

    public String getPath() {
        return items[PATH_INDEX];
    }

    public String getProtocol() {
        return getProtocolInformation(PROTOCOL_INDEX);
    }

    private String getProtocolInformation(int index) {
        final String item = items[PROTOCOL_INFORMATION_SIZE];
        final String[] protocolInformation = item.split(PROTOCOL_DELIMITER);
        if (protocolInformation.length != PROTOCOL_INFORMATION_SIZE) {
            logger.error(INVALID_HTTP_REQUEST);
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST);
        }
        return protocolInformation[index];
    }

    public Double getVersion() {
        return Double.valueOf(getProtocolInformation(VERSION_INDEX));
    }
}
