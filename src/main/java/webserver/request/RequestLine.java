package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestLine {
    private HttpMethod method;
    private Path path;
    private ProtocolVersion protocolVersion;

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String REQUEST_LINE_SPLITTER = " ";


    private RequestLine(HttpMethod method, Path path, ProtocolVersion protocolVersion) {
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
    }

    public static RequestLine findRequestLine(String requestLine) {
        log.info(requestLine);
        String[] str = requestLine.split(REQUEST_LINE_SPLITTER);

        HttpMethod method = HttpMethod.of(str[0]);
        Path path = Path.findPath(str[1]);
        ProtocolVersion protocolVersion = ProtocolVersion.findProtocolAndVersion(str[2]);

        return new RequestLine(method, path, protocolVersion);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
