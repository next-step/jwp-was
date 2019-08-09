package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.http.HttpMethod;
import webserver.http.HttpParameter;

import static utils.ParsingUtils.parseUrl;
import static utils.StringUtils.endSplit;
import static utils.StringUtils.frontSplitWithOrigin;
import static webserver.WebContext.WELCOME_PAGE;

public class RequestLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private static final String ROOT = "/";

    private HttpMethod method;
    private String path;
    private String protocol;
    private HttpParameter httpParameter;

    private static final char URL_QUERY_DELIMITER = '?';
    private static final String REQUEST_LINE_DELIMITER = " ";



    public static class Builder {
        private final HttpMethod method;
        private final String path;
        private final String protocol;
        private HttpParameter httpParameter;

        public Builder(HttpMethod method, String path, String protocol) {
            this.method = method;
            this.path = path;
            this.protocol = protocol;
        }

        public Builder parameter(HttpParameter httpParameter) {
            this.httpParameter = httpParameter;
            return this;
        }

        public RequestLine build() {
            RequestLine requestLine = new RequestLine();
            requestLine.method = this.method;
            requestLine.path = this.path;
            requestLine.protocol = this.protocol;
            requestLine.httpParameter = this.httpParameter;
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
        HttpParameter httpParameter = new HttpParameter(parseUrl(endSplit(requests[1], URL_QUERY_DELIMITER)));

        return new Builder(httpMethod, path, protocol)
                .parameter(httpParameter)
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

    public HttpParameter getHttpParameter() {
        return httpParameter;
    }

}
