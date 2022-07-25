package webserver.request.domain.request;

import org.springframework.util.StringUtils;
import webserver.exception.StringEmptyException;

public class RequestLine {

    private static final String DELIMITER = " ";

    private Method method;
    private Path path;
    private ProtocolInfo protocolInfo;

    private RequestLine(String[] values) {
        init(values);
    }

    public static RequestLine parse(String requestLine) {
        validateRequestline(requestLine);

        String[] values = requestLine.split(DELIMITER);

        return new RequestLine(values);
    }

    private static void validateRequestline(String requestLine) {
        if(requestLine.isBlank()) {
            throw new StringEmptyException("requestLine is empty");
        }
    }

    private void init(String[] values) {
        method = Method.valueOf(values[0]);
        path = Path.parse(values[1]);
        protocolInfo = ProtocolInfo.parse(values[2]);
    }

    public String getMethod() {
        return String.valueOf(method);
    }

    public String parsePath() {
        if (StringUtils.hasText(path.getPath())) {
            return path.getPath();
        }
        return "";
    }

    public String parseQueryString() {
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
