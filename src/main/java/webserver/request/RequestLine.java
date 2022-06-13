package webserver.request;

import webserver.HttpMethod;

public class RequestLine {
    private final HttpMethod method;
    private final String path;
    private final String queryString;
    private final QueryParameters queryParameters;
    private final String protocol;
    private final String version;

    /**
     * HTTP 요청의 Request-Line을 파싱한다.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-5.1">RFC 2616 - 5.1 Request-Line</a>
     */
    public static RequestLine parse(final String httpRequestRequestLine) {
        final String[] tokens = httpRequestRequestLine.split(" ");

        final HttpMethod method = HttpMethod.valueOf(tokens[0]);

        final String[] pathAndQueryString = tokens[1].split("\\?");
        final String path = pathAndQueryString[0];
        final String queryString = (1 < pathAndQueryString.length) ? pathAndQueryString[1] : "";

        final String[] protocolAndVersion = tokens[2].split("/");
        final String protocol = protocolAndVersion[0];
        final String version = protocolAndVersion[1];

        return new RequestLine(method, path, queryString, protocol, version);
    }

    private RequestLine(final HttpMethod method, final String path, final String queryString,
                        final String protocol, final String version) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.queryParameters = new QueryParameters(queryString);
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }

    public String getQueryParameterOrNull(final String key) {
        return this.queryParameters.getParameterOrNull(key);
    }
}
