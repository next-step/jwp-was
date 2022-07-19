package webserver;

import java.util.Map;

public class RequestLine {

    private static final String MESSAGE_EMPTY_SPACE = " ";

    private HttpMethod method;
    private RequestPath path;
    private HttpProtocolVersion protocolVersion;
    private RequestParams requestParams;

    public RequestLine(String requestMessage) {
        String[] splitRequestMessage = requestMessage.split(MESSAGE_EMPTY_SPACE);
        if (splitRequestMessage.length != 3) {
            throw new IllegalArgumentException("잘못된 HTTP 요청 메시지입니다.");
        }
        method = HttpMethod.of(splitRequestMessage[0]);
        path = new RequestPath(splitRequestMessage[1]);
        requestParams = new RequestParams(splitRequestMessage[1]);
        protocolVersion = new HttpProtocolVersion(splitRequestMessage[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getProtocol() {
        return protocolVersion.getProtocol();
    }

    public String getVersion() {
        return protocolVersion.getVersion();
    }

    public Map<String, String> getQueryParsingData() {
        return requestParams.getRequestData();
    }
}
