package model.http;

import exception.IllegalHttpRequestException;

public class Http {
    private static final int VALID_REQUEST_LINE_LENGTH = 3;

    private Method method;
    private PathInformation pathInformation;
    private ProtocolVersion protocolVersion;

    public Http(String requestLine) {
        String[] requestData = requestLine.split(" ");

        if (requestData.length != VALID_REQUEST_LINE_LENGTH) {
            throw new IllegalHttpRequestException("RequestLine은 method, pathInformation, protocol을 공백 기준으로 나누어 전송해야 합니다.");
        }

        this.method = Method.of(requestData[0]);
        this.pathInformation = new PathInformation(requestData[1]);
        this.protocolVersion = new ProtocolVersion(requestData[2]);
    }

    public Method getMethod() {
        return method;
    }

    public PathInformation getPathInformation() {
        return pathInformation;
    }

    public ProtocolVersion getProtocol() {
        return protocolVersion;
    }
}
