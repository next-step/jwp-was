package http.request;

import http.HttpMethod;
import http.request.path.Path;
import http.request.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int HTTP_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    private HttpMethod httpMethod;
    private Path path;
    private Protocol protocol;

    public RequestLine (String url) {
        logger.debug("Request line : {}", url);
        String[] splits = url.split(REQUEST_LINE_DELIMITER);
        validateHttpMethod(splits[HTTP_INDEX]);

        this.httpMethod = HttpMethod.valueOf(splits[HTTP_INDEX]);
        this.path = Path.from(splits[PATH_INDEX]);
        this.protocol = Protocol.from(splits[PROTOCOL_INDEX]);
    }

    private void validateHttpMethod(String httpMethod) {
        if (!httpMethod.equals(httpMethod.toUpperCase())) {
            throw new IllegalArgumentException("Http 메소드는 대문자여야 합니다.");
        }
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getPath() {
        return path.getPathWithOutParam();
    }

    public String getProtocolType() {
        return protocol.getProtocolType();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getParameter(String key) {
        return path.getQueryParameters(key);
    }
}
