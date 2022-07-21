package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RequestLineParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestLineParser.class);
    private static final String INVALID_HTTP_REQUEST = "잘못된 HTTP 요청 ";
    private static final String DELIMITER = " ";
    public static final int PROTOCOL_INFORMATION_SIZE = 2;
    private static final int MINIMUM_REQUEST_LINE_TOKEN = 3;
    private HttpMethod httpMethod;
    private Protocol protocol;

    public RequestLineParser(String line) {
        if (line == null) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST + line);
        }
        String[] item = line.split(DELIMITER);

        if (item.length < MINIMUM_REQUEST_LINE_TOKEN) {
            throw new IllegalArgumentException(INVALID_HTTP_REQUEST + line);
        }

        this.httpMethod = new HttpMethod(item);
        this.protocol = new Protocol(item[PROTOCOL_INFORMATION_SIZE]);
    }

    public String getMethod() {
        return httpMethod.getMethod();
    }

    public String getPath() {
        return httpMethod.getPath();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public Double getVersion() {
        return protocol.getVersion();
    }

    public Map<String, String> getParams() {
        return httpMethod.getParams();
    }
}
