package http;

import java.util.Map;

public class RequestLine {
    private static final String BLANK_ONE = " ";

    private HttpMethod httpMethod;
    private Path path;
    private ProtocolAndVersion protocolAndVersion;

    public RequestLine(String requestLine) {
        String[] strRequestLine = requestLine.split(BLANK_ONE);
        this.httpMethod = HttpMethod.valueOf(strRequestLine[0]);
        this.path = new Path(strRequestLine[1]);
        this.protocolAndVersion = new ProtocolAndVersion(strRequestLine[2]);
    }

    public static RequestLine parse(String strRequestLine) {
        return new RequestLine(strRequestLine);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getStringPath() {
        return path.getPath();
    }

    public Map<String, String> getQueryStrings() {
        return path.getQueryStrings();
    }

    public ProtocolAndVersion getProtocolAndVersion() {
        return protocolAndVersion;
    }
}