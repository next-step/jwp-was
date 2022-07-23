package webserver;

import webserver.domain.HttpMethod;
import webserver.domain.Path;
import webserver.domain.ProtocolInfo;
import webserver.exception.StringEmptyException;

public class RequestLine {

    private static final String DELIMITER = " ";

    private HttpMethod method;
    private Path path;
    private ProtocolInfo protocolInfo;

    private RequestLine(String[] values) {
        parse(values);
    }

    public static RequestLine from(String requestLine) {
        validateRequestline(requestLine);

        String[] values = requestLine.split(DELIMITER);

        return new RequestLine(values);
    }

    private static void validateRequestline(String requestLine) {
        if(requestLine.isBlank()) {
            throw new StringEmptyException("requestLine is empty");
        }
    }

    private void parse(String[] values) {
        method = HttpMethod.valueOf(values[0]);
        path = Path.parse(values[1]);
        protocolInfo = ProtocolInfo.parse(values[2]);
    }

    public String getMethod() {
        return String.valueOf(method);
    }

    public String getPath() {
        return path.getPath();
    }

    public String getQueryString() {
        return path.getQueryString();
    }

    public String getProtocolName() {
        return protocolInfo.getProtocol();
    }

    public String getProtocolVersion() {
        return protocolInfo.getVersion();
    }
}
