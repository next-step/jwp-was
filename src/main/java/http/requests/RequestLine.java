package http.requests;

import http.exceptions.UnsupportedHttpMethodException;
import http.requests.parameters.QueryString;
import http.requests.types.HttpMethod;
import utils.HttpRequestParser;

public class RequestLine {

    // magic number 같아서 좀 더 명시적으로 변경함
    public static final int TOKEN_METHOD_PART = 0;
    public static final int TOKEN_PATH_PART = 1;
    public static final int TOKEN_PROTOCOL_PART = 2;

    // TODO: it's duplicated
    private static final String QUERY_STRING_DELIMITER_REGEX = "\\?";

    private final HttpMethod method;
    private final String path;
    private final Protocol protocol;
    private final QueryString queryString;

    /**
     * @param rawRequestLine 토큰 분리 안한 raw data
     * @see <a href=https://tools.ietf.org/html/rfc2616#section-5.1>Request Line</a>
     * ex: GET /path?key1=value1&key2=value2 HTTP/1.1
     */
    public RequestLine(String rawRequestLine) {
        final String[] tokens = rawRequestLine.split(" ");
        this.method = parseHttpMethod(tokens[TOKEN_METHOD_PART]);
        this.path = parseUri(tokens[TOKEN_PATH_PART]);
        this.queryString = HttpRequestParser.parseQueryString(tokens[TOKEN_PATH_PART]);
        this.protocol = new Protocol(tokens[TOKEN_PROTOCOL_PART]);
    }

    private String parseUri(String rawPathString) {
        return rawPathString.contains("?") ?
                rawPathString.split(QUERY_STRING_DELIMITER_REGEX)[0] :
                rawPathString;
    }

    private HttpMethod parseHttpMethod(String rawHttpMethod) {
        try {
            return HttpMethod.valueOf(rawHttpMethod.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedHttpMethodException("Unsupported Exception!", e);
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

}
