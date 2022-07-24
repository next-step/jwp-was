package webserver.request.domain.request;

import webserver.exception.StringEmptyException;

public class RequestLine {

    private static final String DELIMITER = " ";

    private Method method;
    private Path path;
    private ProtocolInfo protocolInfo;

    private RequestLine(String[] values) {
        parse(values);
    }

    public static RequestLine create(String requestLine) {
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
        method = Method.valueOf(values[0]);
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

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method.toString() +
                ", path=" + path.getQueryString() +
                ", protocol=" + protocolInfo.getProtocol() +
                ", version=" + protocolInfo.getVersion() +
                '}';
    }
}
