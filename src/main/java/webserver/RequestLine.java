package webserver;

public class RequestLine {
    private static final String VALUE_SPERATOR = " ";
    private static final String PROTOCOL_AND_VALUE_SPERATOR = "/";

    private HttpMethod httpMethod;
    private String requestPath;
    private String protocol;
    private String version;

    public RequestLine(String requestValue) {
        String[] values = requestValue.split(VALUE_SPERATOR);
        String[] protocolAndVersion = values[2].split(PROTOCOL_AND_VALUE_SPERATOR);
        this.httpMethod = HttpMethod.valueOf(values[0]);
        this.requestPath = values[1];
        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
