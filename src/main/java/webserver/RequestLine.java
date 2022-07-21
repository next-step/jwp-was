package webserver;

import webserver.domain.Method;
import webserver.domain.Path;
import webserver.domain.ProtocolInfo;

public class RequestLine {

    private Method method;
    private Path path;
    private ProtocolInfo protocolInfo;

    private RequestLine(String requestLine) {
        parse(requestLine);
    }

    public static RequestLine from(String requestLine) {
        validateRequestline(requestLine);

        return new RequestLine(requestLine);
    }

    private static void validateRequestline(String requestLine) {
        if(requestLine.isBlank()) {
            throw new RuntimeException("Exist Not requestLine");
        }
    }

    private void parse(String requestLine) {
        method = Method.parse(requestLine);
        path = Path.parse(requestLine);
        protocolInfo = ProtocolInfo.parse(requestLine);
    }

    public String getMethod() {
        return method.getMethod();
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
