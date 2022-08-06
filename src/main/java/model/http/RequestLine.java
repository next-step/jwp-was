package model.http;

import exception.IllegalHttpRequestException;

public class RequestLine {
    private static final int VALID_REQUEST_LINE_LENGTH = 3;

    private final Method method;
    private final PathInformation pathInformation;
    private final ProtocolVersion protocolVersion;

    public RequestLine(String requestLine) {
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
