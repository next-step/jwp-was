package webserver.http;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    public static final int METHOD_INDEX = 0;
    public static final int URL_INDEX = 1;
    public static final int PROTOCOL_INDEX = 2;

    private final HttpMethod method;
    private final Url url;
    private final ProtocolVersion protocolVersion;

    public RequestLine(HttpMethod method, Url url, ProtocolVersion protocolVersion) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
    }

    public static RequestLine parseFrom(final String startLine){
        String[] httpElement = startLine.split(REQUEST_LINE_DELIMITER);

        HttpMethod httpMethod = HttpMethod.from(httpElement[RequestLine.METHOD_INDEX]);
        Url url = Url.parseFrom(httpElement[RequestLine.URL_INDEX]);
        ProtocolVersion protocol = ProtocolVersion.parseFrom(httpElement[RequestLine.PROTOCOL_INDEX]);

        return new RequestLine(httpMethod, url, protocol);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Url getUrl() {
        return url;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", url=" + url +
                ", protocolVersion=" + protocolVersion +
                '}';
    }
}
