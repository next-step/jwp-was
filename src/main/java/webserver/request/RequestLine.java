package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.HttpMethod;
import webserver.Parameter;

import static utils.StringUtils.endSplit;
import static utils.StringUtils.frontSplitWithOrigin;
import static webserver.Parameter.parseParameter;
import static webserver.RequestHandler.WELCOME_PAGE;

public class RequestLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private static final String ROOT = "/";

    private HttpMethod method;
    private String path;
    private String protocol;
    private Parameter parameter;

    private static final char URL_QUERY_DELIMITER = '?';
    private static final String REQUEST_LINE_DELIMITER = " ";

    public static class Builder {
        private final HttpMethod method;
        private final String path;
        private final String protocol;
        private Parameter parameter;

        Builder(HttpMethod method, String path, String protocol) {
            this.method = method;
            this.path = path;
            this.protocol = protocol;
        }

        Builder parameter(Parameter parameter) {
            this.parameter = parameter;
            return this;
        }

        RequestLine build() {
            RequestLine requestLine = new RequestLine();
            requestLine.method = this.method;
            requestLine.path = this.path;
            requestLine.protocol = this.protocol;
            requestLine.parameter = this.parameter;
            return requestLine;
        }
    }

    public static RequestLine parse(String requestLine) {
        logger.debug("## " + requestLine);
        StringUtils.requireNotBlank(requestLine, "requestLine must be not null");
        String[] requests = requestLine.split(REQUEST_LINE_DELIMITER);

        if (requests.length != 3) {
            throw new IllegalArgumentException("invalid arguments [ " + requestLine + " ]");
        }

        HttpMethod httpMethod = HttpMethod.valueOf(requests[0]);
        String path = getPath(frontSplitWithOrigin(requests[1], URL_QUERY_DELIMITER));
        String protocol = requests[2];
        Parameter parameter = parseParameter(endSplit(requests[1], URL_QUERY_DELIMITER));

        return new Builder(httpMethod, path, protocol)
                .parameter(parameter)
                .build();
    }

    private static String getPath(String path) {
        logger.info("## " + path);
        return ROOT.equals(path) ? WELCOME_PAGE : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Parameter getParameter() {
        return parameter;
    }

}
