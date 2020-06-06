package http;

public class RequestLine {
    private final HttpMethod method;
    private final String path;
    private final Protocol protocol;
    private final QueryString queryString;


    public RequestLine(HttpMethod value, String value1, Protocol protocol) {
        this.method = value;
        this.path = value1;
        this.protocol = protocol;
        this.queryString = null;
    }

    public RequestLine(HttpMethod value, String s, String s1, Protocol protocol) {
        this.method = value;
        this.path = s;
        this.protocol = protocol;
        this.queryString = QueryStringParser.parse(s1);
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

    public String getParameterString() {
        String[] split = this.path.split("\\?");
        return split[1];
    }

    public String getParameter(String key) {
        return queryString.getParameter(key);
    }
}
