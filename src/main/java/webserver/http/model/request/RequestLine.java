package webserver.http.model.request;

import exception.IllegalHttpRequestException;

import java.util.Map;

public class RequestLine {
    private static final int VALID_REQUEST_LINE_LENGTH = 3;

    private final HttpMethod httpMethod;
    private final PathInformation pathInformation;
    private final ProtocolVersion protocolVersion;

    public RequestLine(String requestLine) {
        String[] requestData = requestLine.split(" ");

        if (requestData.length != VALID_REQUEST_LINE_LENGTH) {
            throw new IllegalHttpRequestException("RequestLine은 method, pathInformation, protocol을 공백 기준으로 나누어 전송해야 합니다.");
        }

        this.httpMethod = HttpMethod.of(requestData[0]);
        this.pathInformation = new PathInformation(requestData[1]);
        this.protocolVersion = new ProtocolVersion(requestData[2]);
    }

    public boolean isStaticResource() {
        return pathInformation.isStaticResource();
    }

    public String fullPath() {
        return pathInformation.fullPath();
    }

    public QueryString getQueryString() {
        return pathInformation.getQueryString();
    }

    public Map<String, String> getQueryStringMap() {
        return pathInformation.getQueryString().getQueryStringMap();
    }

    public String path() {
        return pathInformation.path();
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public PathInformation getPathInformation() {
        return pathInformation;
    }

    public ProtocolVersion getProtocol() {
        return protocolVersion;
    }
}
