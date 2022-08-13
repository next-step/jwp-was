package webserver.request;

import webserver.exception.StringEmptyException;

public class RequestLine {

    private static final String DELIMITER = " ";

    private Method method;
    private Path path;
    private ProtocolInfo protocolInfo;

    private RequestLine(String[] values) {
        method = Method.from(values[0]);
        path = Path.parse(values[1]);
        protocolInfo = ProtocolInfo.parse(values[2]);
    }

    public static RequestLine parse(String requestLine) {
        System.out.println(requestLine);

        validateRequestLine(requestLine);

        String[] values = requestLine.split(DELIMITER);

        return new RequestLine(values);
    }

    private static void validateRequestLine(String requestLine) {
        if (requestLine == null || requestLine.isBlank()) {
            throw new StringEmptyException("requestLine is empty");
        }
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public RequestBody getQueryString() {
        return path.getQueryString();
    }

    public String parseProtocol() {
        return protocolInfo.getProtocol();
    }

    public String parseProtocolVersion() {
        return protocolInfo.getVersion();
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method.toString() +
                ", path=" + ((path.getQueryString() == null) ? "" : path.getQueryString()) +
                ", protocol=" + protocolInfo.getProtocol() +
                ", version=" + protocolInfo.getVersion() +
                '}';
    }
}
