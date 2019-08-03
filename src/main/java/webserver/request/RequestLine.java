package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MapUtils;
import utils.StringUtils;
import webserver.HttpMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class RequestLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private HttpMethod method;
    private String path;
    private String protocol;
    private Map<String, String> queryMap;

    public static class Builder {
        private final HttpMethod method;
        private final String path;
        private final String protocol;
        private Map<String, String> queryMap;

        Builder(HttpMethod method, String path, String protocol) {
            this.method = method;
            this.path = path;
            this.protocol = protocol;
        }

        Builder queryMap(Map<String, String> queryMap) {
            this.queryMap = queryMap;
            return this;
        }

        RequestLine build() {
            RequestLine requestLine = new RequestLine();
            requestLine.method = this.method;
            requestLine.path = this.path;
            requestLine.protocol = this.protocol;
            requestLine.queryMap = this.queryMap;
            return requestLine;
        }
    }

    public static RequestLine parse(String requestLine) {

        StringUtils.requireNotBlank(requestLine, "requestLine must be not null");
        String[] requests = requestLine.split(" ");

        if (requests.length != 3) {
            throw new IllegalArgumentException("invalid arguments [ " + requestLine + " ]");
        }

        HttpMethod httpMethod = HttpMethod.valueOf(requests[0]);
        String path = frontSplitWithOrigin(requests[1], '?');
        String protocol = requests[2];
        Map<String, String> queryMap = getQueryMap(endSplit(requests[1], '?'));

        logger.debug("## parse request-line: httpMethod: {}, path: {}", httpMethod.name(), path);

        return new Builder(httpMethod, path, protocol)
                .queryMap(queryMap)
                .build();
    }

    /**
     * @param query is key=value string split '&'
     * @return key/value query map
     */
    private static Map<String, String> getQueryMap(String query) {
        String[] parameters = query.split("&");

        if (parameters.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> queryMap = new HashMap<>(parameters.length);
        for (String parameter : parameters) {
            MapUtils.putIfKeyNotBlank(queryMap,
                    frontSplitWithOrigin(parameter, '='),
                    endSplit(parameter, '='));
        }
        return queryMap;
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

    public Map<String, String> getQueryMap() {
        return queryMap;
    }

}
